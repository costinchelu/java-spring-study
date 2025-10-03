package io.spring.batch.domain;

import java.io.Serializable;
import java.time.LocalDateTime;


public record Customer (long id, String firstName, String lastName, LocalDateTime birthdate) implements Serializable {
}