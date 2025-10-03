package io.spring.batch.domain;

import org.springframework.batch.item.ItemProcessor;


public class UpperCaseItemProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer item) {
		return new Customer(item.id(),
				item.firstName().toUpperCase(),
				item.lastName().toUpperCase(),
				item.birthdate());
	}
}
