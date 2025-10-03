package io.spring.batch.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xmlutil.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@XmlRootElement(name="customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {

    @XmlElement(name = "id")
    private long id;

    @XmlElement(name = "firstName")
    private String firstName;

    @XmlElement(name = "lastName")
    private String lastName;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @XmlElement(name = "birthdate")
    private LocalDateTime birthdate;
}
