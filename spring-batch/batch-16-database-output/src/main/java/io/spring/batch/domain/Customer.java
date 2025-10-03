package io.spring.batch.domain;

import java.util.Date;

public record Customer(long id, String firstName, String lastName, Date birthdate) {
}
