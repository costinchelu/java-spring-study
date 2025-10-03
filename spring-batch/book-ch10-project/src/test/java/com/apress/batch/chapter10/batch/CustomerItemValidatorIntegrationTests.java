package com.apress.batch.chapter10.batch;

import javax.sql.DataSource;

import com.apress.batch.chapter10.domain.CustomerUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@JdbcTest
@Disabled
class CustomerItemValidatorIntegrationTests {

	@Autowired
	private DataSource dataSource;

	private CustomerItemValidator customerItemValidator;

	@BeforeEach
	public void setUp() {
		this.customerItemValidator = new CustomerItemValidator(this.dataSource);
	}

	@Test
	void noCustomersTest() {
		CustomerUpdate customerUpdate = new CustomerUpdate(-5L);
		ValidationException exception =
				assertThrows(ValidationException.class,
						() -> this.customerItemValidator.validate(customerUpdate));
		assertEquals("Customer id -5 was not able to be found",
				exception.getMessage());

	}

	@Test
	void customersTest() {
		CustomerUpdate customerUpdate = new CustomerUpdate(5L);
		this.customerItemValidator.validate(customerUpdate);
	}
}
