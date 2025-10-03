package demoCoach;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyApp {

    private final static Logger log = LoggerFactory.getLogger(MyApp.class);

    public static void main(String[] args) {

        // create the object
        Coach theCoach = new BaseballCoach();

        // use the object
        System.out.println(theCoach.getDailyWorkout());
        log.info("We are using " + theCoach.getClass().toString());

        theCoach = new TrackCoach();
        System.out.println(theCoach.getDailyWorkout());
        log.info("Now we are using " + theCoach.getClass().toString());
    }
}
