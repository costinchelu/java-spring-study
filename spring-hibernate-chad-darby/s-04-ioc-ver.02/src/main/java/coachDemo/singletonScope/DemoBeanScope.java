package coachDemo.singletonScope;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoBeanScope {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanScope-applicationContext.xml");

        Coach theCoach = context.getBean("myCoach", Coach.class);
        Coach alphaCoach = context.getBean("myCoach", Coach.class);

        // check to see if they are the same beans
        System.out.println("Are both beans the same reference? " + (theCoach == alphaCoach));
        System.out.println("Memory location for theCoach: " + theCoach);
        System.out.println("Memory location for alphaCoach: " + alphaCoach);

        context.close();
    }
}
