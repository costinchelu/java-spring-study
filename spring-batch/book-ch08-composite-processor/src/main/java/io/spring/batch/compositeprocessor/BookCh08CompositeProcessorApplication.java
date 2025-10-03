package io.spring.batch.compositeprocessor;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BookCh08CompositeProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookCh08CompositeProcessorApplication.class, "customerFile=/input/customer.csv");
    }

}
