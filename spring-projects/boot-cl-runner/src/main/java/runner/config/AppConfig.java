package runner.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import runner.components.SomeBean;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner initCode(SomeBean someBean) {
        return args -> System.out.println(someBean.someMethod() + ">>>> using @CommandLineRunner bean...");
    }
}
