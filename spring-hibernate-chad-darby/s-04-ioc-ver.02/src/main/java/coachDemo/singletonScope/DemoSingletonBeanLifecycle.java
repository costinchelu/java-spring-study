package coachDemo.singletonScope;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoSingletonBeanLifecycle {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanLifecycleSingleton-applicationContext.xml");

        Coach theCoach = context.getBean("myCoach", Coach.class);

        System.out.println(theCoach.getDailyWorkout());

        context.close();
    }
}
