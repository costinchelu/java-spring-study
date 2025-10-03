package demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import demo.dao.CustomerDAO;
import demo.dao.EmployeeDAO;
import demo.entity.Customer;
import demo.entity.Employee;

@Controller
public class DemoController {

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@GetMapping("/")
	public String getData(Model theModel) {
		
		// customers
		List<Customer> theCustomers = customerDAO.getCustomers();
		theModel.addAttribute("customers", theCustomers);
		
		// employees
		List<Employee> theEmployees = employeeDAO.getEmployees();
		theModel.addAttribute("employees", theEmployees);
		
		return "display-results";
	}
}
