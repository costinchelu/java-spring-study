package io.spring.batch.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.stereotype.Component;

@Component
public class CustomerLineAggregator implements LineAggregator<Customer> {

	// we are serializing database input to a JSON file
	private final ObjectMapper objectMapper = new ObjectMapper();

	public CustomerLineAggregator() {
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	@Override
	public String aggregate(Customer item) {
		try {
			return objectMapper.writeValueAsString(item);
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to serialize Customer", e);
		}
	}
}
