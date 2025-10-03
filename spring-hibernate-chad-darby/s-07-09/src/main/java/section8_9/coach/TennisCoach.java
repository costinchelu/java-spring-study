package section8_9.coach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import section8_9.services.FortuneService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
//@Scope("prototype")
public class TennisCoach implements Coach {

    // field injection (this is equivalent to a setter on that field (Java is using reflection)
    @Autowired
    @Qualifier("fileFortuneService")
    private FortuneService fortuneService;

    // value will be injected from the .properties file
    @Value("$(foo.team)")
    private String email;

    // example for setter injection
    public TennisCoach() {
        System.out.println("TennisCoach >> inside default constructor");
    }

    // constructor dependency injection through annotations (optional from Spring 4.3)
//    @Autowired
//    public TennisCoach(FortuneService fortuneService) {
//        this.fortuneService = fortuneService;
//    }

    @Override
    public String getDailyWorkout() {
        return "Tennis practice";
    }

    @Override
    public String getDailyFortune() {
        return "Tennis coach -> " + fortuneService.getFortune();
    }


    // setter dependency injection through annotations
//    @Autowired
//    public void setFortuneService(FortuneService fortuneService) {
//        System.out.println("TennisCoach >> Spring is setting through autowiring");
//        this.fortuneService = fortuneService;
//    }

//    // method injection:
//    @Autowired
//    public void doSomeStuff(FortuneService fortuneService) {
//        System.out.println("Method injection!");
//        this.fortuneService = fortuneService;
//    }

    // Will execute after constructor and dependency injection
    @PostConstruct
    public void methodRunAfterConstruction() {
        System.out.println("PostConstruct method run");
    }

    // this will run only on singleton scope
    @PreDestroy
    public void methodRunBeforeDestruction() {
        System.out.println("PreDestroy method run");
    }
}
