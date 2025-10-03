package section10.coach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import section10.services.FortuneService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.naming.directory.InvalidAttributesException;


@Component
public class TennisCoach implements Coach {

    @Autowired
    @Qualifier("fileFortuneService")
    private FortuneService fortuneService;


    public TennisCoach() {
        System.out.println("TennisCoach >> inside default constructor");
    }

    @Override
    public String getDailyWorkout() {
        return "Tennis practice";
    }

    @Override
    public String getDailyFortune() {
        return "Tennis coach -> " + fortuneService.getFortune();
    }

    @Override
    public String getInfo() {
        try {
            throw new InvalidAttributesException();
        } catch (InvalidAttributesException e) {
            e.printStackTrace();
        }
        return "No attributes";
    }

    @PostConstruct
    public void methodRunAfterConstruction() {
        System.out.println("PostConstruct method run");
    }

    @PreDestroy
    public void methodRunBeforeDestruction() {
        System.out.println("PreDestroy method run");
    }
}
