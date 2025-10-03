package demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import demo.entity.Customer;
import demo.entity.Employee;
import demo.service.MultiDataSourceService;

@Controller
public class DemoController {

	@Autowired
	private MultiDataSourceService multiDataSourceService;
	
	@GetMapping("/")
	public String getData(Model theModel) {
		
		// customers
		List<Customer> theCustomers = multiDataSourceService.getCustomers();
		theModel.addAttribute("customers", theCustomers);
		
		// employees
		List<Employee> theEmployees = multiDataSourceService.getEmployees();
		theModel.addAttribute("employees", theEmployees);
		
		return "display-results";
	}
}
