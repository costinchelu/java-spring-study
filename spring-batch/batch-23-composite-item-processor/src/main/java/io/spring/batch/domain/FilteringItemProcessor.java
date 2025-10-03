package io.spring.batch.domain;

import org.springframework.batch.item.ItemProcessor;


public class FilteringItemProcessor implements ItemProcessor<Customer, Customer> {
	@Override
	public Customer process(Customer item) {

		if(item.id() % 2 == 0) {
			return null;
		}
		else {
			return item;
		}
	}
}
