package io.spring.batch.domain;

import java.util.Date;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.batch.item.ResourceAware;
import org.springframework.core.io.Resource;

// when we are reading from multiple files, it can be useful to know where they are coming from (ResourceAware is not required here)
@RequiredArgsConstructor
@ToString
public class Customer implements ResourceAware {

	private final long id;

	private final String firstName;

	private final String lastName;

	private final Date birthdate;

	private Resource resource;

	@Override
	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
