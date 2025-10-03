package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    // need a controller method to show the initial HTML form
    @RequestMapping("/showForm")
    public String showForm() {
        return "helloworld-form";
    }

    // need a controller method to process the HTML form (version 1)
    @RequestMapping("/processForm")
    public String processForm() {
        return "response-form";
    }

    // need a controller method to read form data and add data to the model (version 2)
    @RequestMapping("/processFormVersionTwo")
    public String processFormVer2(HttpServletRequest request, Model model) {

        // read request parameter from the HTML form
        String theName = request.getParameter("userName");

        // convert data received to all caps (process data) to prepare for model
        String processedData = "Hello user " + theName.toUpperCase();

        // add processed message to the model
        model.addAttribute("userMessage", processedData);

        return "response-form";
    }

    // will read the form parameter and bind it to the string parameter we provide (version 3)
    @RequestMapping("/processFormVersionThree")
    public String processFormVer3(
            @RequestParam("userName") String theName, Model model) {

        // convert data received to all caps (process data) to prepare for model
        String processedData = "Hello user " + theName.toUpperCase() + ". (ver. 3)";

        // add processed message to the model
        model.addAttribute("userMessage", processedData);

        return "response-form";
    }
}
