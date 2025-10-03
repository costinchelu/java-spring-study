package demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.dao.CustomerDAO;
import demo.dao.EmployeeDAO;
import demo.entity.Customer;
import demo.entity.Employee;

@Service
public class MultiDataSourceServiceImpl implements MultiDataSourceService {

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private EmployeeDAO employeeDAO;

	
	@Override
	public List<Customer> getCustomers() {
		return customerDAO.getCustomers();
	}

	@Override
	public List<Employee> getEmployees() {
		return employeeDAO.getEmployees();
	}

}
