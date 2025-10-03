package coachDemo.prototypeScope;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoPrototypeBeanLifecycle {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanLifecyclePrototype-applicationContext.xml");

        Coach theCoach = context.getBean("myCoach", Coach.class);

        System.out.println(theCoach.getDailyWorkout());

        context.close();
    }
}
