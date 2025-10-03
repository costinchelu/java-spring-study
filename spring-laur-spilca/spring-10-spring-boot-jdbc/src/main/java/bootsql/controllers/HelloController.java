package bootsql.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestController
public class HelloController {

    @PostMapping(path = "/test/{language}")
    public String test(
            @PathVariable String language,
            @RequestHeader String a,
            @RequestHeader String b,
            @RequestHeader String c,
            @RequestBody String body,
            HttpServletResponse response
    ) {
        response.addHeader("test", "The header was added successfully");
        return language + " " + a + " " + b + " " + c + " " + body;
    }

    @GetMapping(path = "/all-headers")
    public Map<String, String> all(@RequestHeader Map<String, String> map) {
        return map;
    }

    @GetMapping(path = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] file() {
        return new byte[1000];
    }


}
