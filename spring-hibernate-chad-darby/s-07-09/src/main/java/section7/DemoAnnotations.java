package section7;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@SpringBootApplication
public class DemoAnnotations {

    public static void main(String[] args) {

        // read Sring config file
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");

        // get the bean from the Spring container
        Coach theCoach = context.getBean("tCoach", Coach.class);
        Coach secondCoach = context.getBean("swimmingCoach", Coach.class);

        // call a method on the bean
        System.out.println(theCoach.getDailyWorkout());
        System.out.println(secondCoach.getDailyWorkout());

        // close the context
        context.close();
    }
}
