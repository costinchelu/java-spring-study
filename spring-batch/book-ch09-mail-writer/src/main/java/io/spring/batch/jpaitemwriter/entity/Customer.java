package io.spring.batch.jpaitemwriter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String firstName;

    private String middleInitial;

    private String lastName;

    private String address;

    private String city;

    private String state;

    private String zip;

    private String email;
}

