package com.apress.batch.chapter10.batch;

import java.util.Map;

import com.apress.batch.chapter10.domain.CustomerUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


class CustomerItemValidatorTests {

	@Mock
	private NamedParameterJdbcTemplate template;

	private CustomerItemValidator validator;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.validator = new CustomerItemValidator(this.template);
	}

	@Test
	void validCustomerTest() {

		// given

		CustomerUpdate customer = new CustomerUpdate(5L);

		// when
		ArgumentCaptor<Map<String, Long>> parameterMap = ArgumentCaptor.forClass(Map.class);
		when(this.template.queryForObject(eq(CustomerItemValidator.FIND_CUSTOMER),
										parameterMap.capture(),
										eq(Long.class)))
				.thenReturn(2L);

		this.validator.validate(customer);

		// then

		assertEquals(5L, (long) parameterMap.getValue().get("id"));
	}

	@Test
	void invalidCustomerTest() {

		// given

		CustomerUpdate customerUpdate = new CustomerUpdate(5L);

		// when
		ArgumentCaptor<Map<String, Long>> parameterMap = ArgumentCaptor.forClass(Map.class);
		when(this.template.queryForObject(eq(CustomerItemValidator.FIND_CUSTOMER),
										parameterMap.capture(),
										eq(Long.class)))
				.thenReturn(0L);

		Throwable exception = assertThrows(ValidationException.class,
				() -> this.validator.validate(customerUpdate));

		// then

		assertEquals("Customer id 5 was not able to be found", exception.getMessage());
	}
}
