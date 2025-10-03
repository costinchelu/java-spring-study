package io.spring.batch.validation;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class ValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidationApplication.class, "customerFile=/input/customer.csv");
    }

}
