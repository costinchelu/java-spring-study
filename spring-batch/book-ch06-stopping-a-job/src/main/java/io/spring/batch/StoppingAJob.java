package io.spring.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableBatchProcessing
public class StoppingAJob {

    public static void main(String[] args) {
        List<String> realArgs = new ArrayList<>(Arrays.asList(args));

        realArgs.add("transactionFile=input/transactionFile.csv");
        realArgs.add("summaryFile=output/summaryFile.csv");

        SpringApplication.run(StoppingAJob.class, realArgs.toArray(new String[realArgs.size()]));
    }

}
