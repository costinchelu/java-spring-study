package restdemo.dao;

import java.util.List;

import restdemo.entity.Customer;


public interface CustomerDAO {

	List<Customer> getCustomers();

	void saveCustomer(Customer customer);

	Customer getCustomer(int customerId);

	void deleteCustomer(int customerId);
	
}
