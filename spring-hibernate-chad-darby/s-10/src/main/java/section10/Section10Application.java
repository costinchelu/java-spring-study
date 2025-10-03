package section10;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import section10.coach.Coach;


public class Section10Application {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SportConfig.class);
        Coach theCoach = context.getBean("swimCoach", Coach.class);

        System.out.println(theCoach.getDailyWorkout());
        System.out.println(theCoach.getDailyFortune());
        System.out.println(theCoach.getInfo());

        context.close();
    }
}
