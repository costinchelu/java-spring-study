package coachDemo.singletonScope;

public class TrackCoach implements Coach {

    private FortuneService fortuneService;

    public TrackCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Daily workout of running...";
    }

    @Override
    public String getDailyFortune() {
        return "Just do it! " + fortuneService.getFortune();
    }

    // add an init method
    public void doMyStartupStuff() {
        System.out.println("TrackCoach: inside method doMyStartupStuff");
    }

    // add an destroy method
    public void doMyCleanupStuff() {
        System.out.println("TrackCoach: inside method doMyCleanupStuff");
    }
}
