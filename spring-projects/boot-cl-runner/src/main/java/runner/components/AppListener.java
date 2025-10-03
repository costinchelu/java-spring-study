package runner.components;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppListener implements ApplicationListener<ContextRefreshedEvent> {

    private SomeBean someBean;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println(someBean.someMethod() + ">>>>> using @ApplicationListener class ...");
    }
}
