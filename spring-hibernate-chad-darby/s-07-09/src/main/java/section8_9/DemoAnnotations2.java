package section8_9;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import section8_9.coach.Coach;


@SpringBootApplication
public class DemoAnnotations2 {

    public static void main(String[] args) {
        //System.out.println(System.getProperty("user.dir"));

        // read Sring config file
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");

        // get the bean from the Spring container
        Coach theCoach = context.getBean("tennisCoach", Coach.class);

        // call a method on the bean
        System.out.println(theCoach.getDailyWorkout());
        System.out.println(theCoach.getDailyFortune());

        // close the context
        context.close();
    }
}
