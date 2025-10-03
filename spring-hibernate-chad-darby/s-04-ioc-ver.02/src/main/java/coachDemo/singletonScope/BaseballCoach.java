package coachDemo.singletonScope;

public class BaseballCoach implements Coach {

    private FortuneService fortuneService;

    public BaseballCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Daily workout of ball throwing";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }
}
