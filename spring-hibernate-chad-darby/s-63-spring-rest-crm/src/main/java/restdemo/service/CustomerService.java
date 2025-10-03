package restdemo.service;

import java.util.List;

import restdemo.entity.Customer;


public interface CustomerService {

	List<Customer> getCustomers();

	void saveCustomer(Customer customer);

	Customer getCustomer(int customerId);

	void deleteCustomer(int customerId);
	
}
