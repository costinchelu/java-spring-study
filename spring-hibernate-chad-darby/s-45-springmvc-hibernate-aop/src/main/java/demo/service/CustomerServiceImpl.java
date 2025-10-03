package demo.service;

import demo.dao.CustomerDAO;
import demo.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {

    // inject CustomerDAO
    @Autowired
    private CustomerDAO customerDAO;


    // FACADE design pattern

    // by using Transactional annotation we will let Hibernate manage connection and disconnection to DB
    @Override
    @Transactional
    public List<Customer> getCustomers() {
        return customerDAO.getCustomers();
    }


    // method used to add a new customer to the database
    @Override
    @Transactional
    public void saveCustomer(Customer customer) {
        customerDAO.saveCustomer(customer);
    }

    @Override
    @Transactional
    public Customer getCustomer(int customerId) {
        return customerDAO.getCustomer(customerId);
    }

    @Override
    @Transactional
    public void deleteCustomer(int customerId) {
        customerDAO.deleteCustomer(customerId);
    }

    @Override
    @Transactional
    public List<Customer> searchCustomers(String searchName) {
        return customerDAO.searchCustomers(searchName);
    }
}
