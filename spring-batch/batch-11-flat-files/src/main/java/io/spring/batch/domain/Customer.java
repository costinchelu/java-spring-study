package io.spring.batch.domain;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@ToString
public class Customer {

	private final long id;

	private final String firstName;

	private final String lastName;

	private final Date birthdate;
}
