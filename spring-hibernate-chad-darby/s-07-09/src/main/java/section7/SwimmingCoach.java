package section7;

import org.springframework.stereotype.Component;

// implicit bean id:
// this will make swimmingCoach as bean id (transforming first uppercase letter
// from class name to a minor case letter)

@Component
public class SwimmingCoach implements Coach {

    @Override
    public String getDailyWorkout() {
        return "Swimming practice";
    }
}
