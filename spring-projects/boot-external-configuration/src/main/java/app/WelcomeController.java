package app;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/")
public class WelcomeController {

    private AppProperties app;

    private GlobalProperties global;

    private CustomProperties custom;

    @GetMapping("message")
    public String welcome(Map<String, Object> model) {

        String appProperties = app.toString();
        String globalProperties = global.toString();
        String customProperties = custom.toString();

        String appPropertiesCompiler = app.getCompiler().toString();

        log.debug("Welcome {}, {}, {}", app, global, custom);

        model.put("appProperties", appProperties);
        model.put("globalProperties", globalProperties);
        model.put("customProperties", customProperties);
        model.put("appPropertiesCompiler", appPropertiesCompiler);

        return "welcome";
    }
}