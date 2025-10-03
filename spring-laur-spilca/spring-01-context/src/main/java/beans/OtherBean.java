package beans;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * @Component will tell Spring that this class will create an instance that is part of the context
 * the Repository, Controller, Service... are also stereotypes
 * (putting Controller instead of Component will get us the same result, but by using specific annotations
 * it is easier to see the exact purpose of that class)
 *
 * @PostConstruct instructs Spring that it has to call this method immediately after the creation of the component
 */
@Component
public class OtherBean {

    private String text;


    @PostConstruct
    private void init() {
        this.text = "Hello 4";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
