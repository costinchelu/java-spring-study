package runner.components;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommandLRComponent implements CommandLineRunner {

    private SomeBean someBean;

    @Override
    public void run(String... args) {
        System.out.println(someBean.someMethod() + ">>>>> using @CommandLineRunner class ...");
    }
}
