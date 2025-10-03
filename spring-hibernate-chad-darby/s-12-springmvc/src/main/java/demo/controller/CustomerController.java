package demo.controller;

import demo.model.Customer;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/customer")
public class CustomerController {


    // add an initBinder - to convert trim input strings (or wrapper classes) (remove leading and trailing whitespace)
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping("/showForm")
    public String showForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    @RequestMapping("/processForm")
    public String processForm(
            @Valid @ModelAttribute("customer") Customer customer,
            BindingResult bindingResult) {

        /* When performing Spring MVC validation, the location of the BindingResult parameter is very important.
        In the method signature, the BindingResult parameter must appear immediately after the model attribute.
        If you place it in any other location, Spring MVC validation will not work as desired. */

        if(bindingResult.hasErrors()) {
            return "customer-form";
        } else {
            return "customer-confirmation";
        }
    }
}
