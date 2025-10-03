package section10.coach;

import org.springframework.beans.factory.annotation.Value;
import section10.services.FortuneService;

public class SwimCoach implements Coach {

    private FortuneService fortuneService;

    @Value("${foo.email}")
    private String email;

    @Value("${foo.team}")
    private String team;


    public SwimCoach(FortuneService fortuneService) {
        System.out.println("Swim Coach >> inside constructor");
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Swimming training...";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }

    @Override
    public String getInfo() {
        return "Properties:\n\t" + email + "\n\t" + team;
    }

}
