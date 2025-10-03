package section7;

import org.springframework.stereotype.Component;

// explicit bean id:

@Component("tCoach")
public class TennisCoach implements Coach {

    @Override
    public String getDailyWorkout() {
        return "Tennis practice";
    }
}
