package coachDemo.singletonScope;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoSetterInjection {

    public static void main(String[] args) {

        // load the spring config file
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // retrieve bean from spring container
        ExtendedCoach theCoach = context.getBean("myCricketCoach", ExtendedCoach.class);
        // call methods on the bean
        System.out.println(theCoach.getDailyWorkout());
        System.out.println(theCoach.getDailyFortune());
        System.out.println(theCoach.getEmailAddress());
        System.out.println(theCoach.getTeam());
        // close the context
        context.close();
    }
}
