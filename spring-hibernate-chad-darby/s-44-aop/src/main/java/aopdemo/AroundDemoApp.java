package aopdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import aopdemo.service.TrafficFortuneService;

import java.util.logging.Logger;

public class AroundDemoApp {

    private static Logger myLogger = Logger.getLogger(AroundDemoApp.class.getName());



    public static void main(String[] args) {

        // read Spring config Java Class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);


        TrafficFortuneService traffic = context.getBean("trafficFortuneService", TrafficFortuneService.class);


        // to simulate an exception (true for generating exception, false for no exception)
        boolean tripWire = true;
        String fortune = null;

        // if the exception is handled in the Aspect method, main will never know an exception happened
        // else we have to treat it
        try {
            fortune = traffic.getFortune(tripWire);
        } catch (Exception e) {
            myLogger.warning("EXCEPTION CATCH IN MAIN");
            fortune = "We are getting a default fortune";
        }

        myLogger.info("\nMAIN PROGRAM: AroundDemoApp -> " + fortune);

        context.close();
    }
}
