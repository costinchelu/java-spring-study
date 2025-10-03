package slf4jdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/")
    public String hello(Model model) {
        List<Integer> data = List.of(1, 2, 3, 4, 5);
        logger.debug("Hello from Logback {}", data);
        model.addAttribute("num", data);
        return "index";
    }

}