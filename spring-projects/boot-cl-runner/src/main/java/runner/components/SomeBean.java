package runner.components;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SomeBean {

    private static final String MESSAGE = "Run date: ";

    public String someMethod() {
        return MESSAGE + new Date();
    }
}
