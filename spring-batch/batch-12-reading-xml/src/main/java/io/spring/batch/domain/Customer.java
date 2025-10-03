package io.spring.batch.domain;

import io.spring.batch.xmlutil.LocalDateTimeAdapter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@ToString
@XmlRootElement(name="customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {

	private long id;

	private String firstName;

	private String lastName;

	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime birthdate;
}
