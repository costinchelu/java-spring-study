package springsecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {
//        return "plain-login";
        return "fancy-login";
    }

    // request mapping for "Access Denied"
    @GetMapping("/access-denied")
    public String error403() {
        return "access-denied";
    }
}
