package io.spring.batch.domain;

import java.time.LocalDateTime;

public record Customer(long id, String firstName, String lastName, LocalDateTime birthdate) {

}