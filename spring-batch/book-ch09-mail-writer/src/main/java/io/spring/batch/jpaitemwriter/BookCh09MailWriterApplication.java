package io.spring.batch.jpaitemwriter;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableBatchProcessing
public class BookCh09MailWriterApplication {

    public static void main(String[] args) {
        List<String> newArgs = new ArrayList<>(3);
        newArgs.add("customerFile=/input/customerWithEmail.csv");

        SpringApplication.run(BookCh09MailWriterApplication.class, newArgs.toArray(new String[0]));
    }

}
