package demo.controller;


import demo.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import demo.service.CustomerService;

import java.util.List;


@Controller
@RequestMapping("/customer")
public class CustomerController {

    // need to inject the customer Service
    @Autowired
    private CustomerService customerService;


    // displaying the list of customers
    @GetMapping("/list")
    public String listCustomers(Model theModel) {

        // get customers from DAO
        List<Customer> allCustomers = customerService.getCustomers();

        // add the customers to the model
        theModel.addAttribute("customers", allCustomers);

        return "list-customers";
    }

    // displaying customer form (for new customer or update customer)
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        // create a new model to bind form data
        Customer newCustomer = new Customer();
        theModel.addAttribute("customer", newCustomer);

        return "customer-form";
    }

    // saving customer (received from the customer-list)
    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {

        // save customer using our service - this will be a conditional save that will be decided in the
        // DAO. If customer has a null id, that means we want to insert a new customer, else we will
        // update an existing customer
        customerService.saveCustomer(customer);

        // return to the list of customers
        return "redirect:/customer/list";
    }

    // populating customer list with customer details (when update)
    @GetMapping("showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") int customerId, Model theModel) {

        // get the customer based on id
        Customer customer = customerService.getCustomer(customerId);

        // add customer as a model attribute (to populate the form)
        theModel.addAttribute("customer", customer);

        // go to the customer form
        return "customer-form";
    }

    // delete customer
    @GetMapping("delete")
    public String deleteCustomer(@RequestParam("customerId") int customerId) {

        customerService.deleteCustomer(customerId);

        return "redirect:/customer/list";
    }

    // for the search box
    @GetMapping("/search")
    public String searchCustomers(@RequestParam("searchName") String searchName, Model theModel) {

        List<Customer> customers = customerService.searchCustomers(searchName);

        theModel.addAttribute("customers", customers);

        return "list-customers";
    }

}
