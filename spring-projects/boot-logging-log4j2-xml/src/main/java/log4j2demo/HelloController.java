package log4j2demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//https://docs.spring.io/spring-boot/docs/current/reference/html/howto-logging.html
@Controller
public class HelloController {

    private static final Logger logger = LogManager.getLogger(HelloController.class);

    private final List<Integer> intList = List.of(1, 2, 3, 4, 5);

    @GetMapping("/")
    public String mainMapping(Model model) {

        // pre-java 8
        if (logger.isDebugEnabled()) {
            logger.debug("Hello from Log4j 2 - num : {}", intList);
        }

        // java 8 lambda, no need to check log level
        logger.debug("Hello from Log4j 2 - num : {}", () -> intList);

        model.addAttribute("tasks", intList);

        return "welcome"; //view
    }
}
