package main;


import config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.HelloService;


public class Main {

    public static void main(String[] args) {

        try (var c = new AnnotationConfigApplicationContext(ProjectConfig.class)) {

            HelloService service = c.getBean(HelloService.class);

            service.hello("John");

            String result = service.hello2("Michael");
            System.out.println("Result is: " + result);

            service.boo();

        } catch (RuntimeException e) {
            System.out.println(e.getMessage() + "\tException was thrown!");
        }
    }
}
