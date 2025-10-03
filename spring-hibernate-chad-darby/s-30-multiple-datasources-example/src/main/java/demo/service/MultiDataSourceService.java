package demo.service;

import java.util.List;

import demo.entity.Customer;
import demo.entity.Employee;

public interface MultiDataSourceService {

	public List<Customer> getCustomers();
	
	public List<Employee> getEmployees();
	
}
