package coachDemo.singletonScope;

public class CricketCoach implements ExtendedCoach {

    private FortuneService fortuneService;
    private String emailAddress;
    private String team;


    public CricketCoach() {
        System.out.println("CricketCoach: called no-arg constructor");
    }

    @Override
    public String getDailyWorkout() {
        return "Daily cricket workout";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }

    public void setFortuneService(FortuneService fortuneService) {
        System.out.println("CricketCoach: called setter for FortuneService dependency");
        this.fortuneService = fortuneService;
    }

    public void setEmailAddress(String emailAddress) {
        System.out.println("CricketCoach: called setter for emailAddress attribute");
        this.emailAddress = emailAddress;
    }

    public void setTeam(String team) {
        System.out.println("CricketCoach: called setter for team attribute");
        this.team = team;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getTeam() {
        return team;
    }
}
