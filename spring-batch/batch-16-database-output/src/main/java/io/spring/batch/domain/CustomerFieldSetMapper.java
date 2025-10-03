package io.spring.batch.domain;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;


public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {

	@Override
	public Customer mapFieldSet(FieldSet fieldSet) {
		return new Customer(fieldSet.readLong("id"),
				fieldSet.readString("firstName"),
				fieldSet.readString("lastName"),
				fieldSet.readDate("birthdate", "dd-MM-y HH:mm"));
	}
}
