package io.spring.batch.compositeprocessor.service;

import io.spring.batch.compositeprocessor.domain.Customer;
import org.springframework.stereotype.Service;


@Service
public class CaseService {

    public Customer upperCase(Customer customer) {
        Customer newCustomer = new Customer(customer);
        newCustomer.setFirstName(newCustomer.getFirstName().toUpperCase());
        newCustomer.setMiddleInitial(newCustomer.getMiddleInitial().toUpperCase());
        newCustomer.setLastName(newCustomer.getLastName().toUpperCase());
        return newCustomer;
    }

    public Customer lowerCase(Customer customer) {
        Customer newCustomer = new Customer(customer);
        newCustomer.setAddress(newCustomer.getAddress().toLowerCase());
        newCustomer.setState(newCustomer.getState().toLowerCase());
        newCustomer.setCity(newCustomer.getCity().toLowerCase());
        return newCustomer;
    }
}
