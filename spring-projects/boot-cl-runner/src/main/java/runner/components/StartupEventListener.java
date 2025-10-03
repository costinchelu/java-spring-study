package runner.components;

import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StartupEventListener {

    private SomeBean someBean;

    @EventListener
    public void runSomething(ContextRefreshedEvent event) {
        System.out.println(someBean.someMethod() + ">>>>> using @EventListener ...");
    }
}
