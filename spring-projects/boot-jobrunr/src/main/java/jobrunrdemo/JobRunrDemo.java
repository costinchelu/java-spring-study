package jobrunrdemo;

import jobrunrdemo.config.MainConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MainConfiguration.class)
public class JobRunrDemo {

    public static void main(String[] args) {
        SpringApplication.run(JobRunrDemo.class, args);
    }

}