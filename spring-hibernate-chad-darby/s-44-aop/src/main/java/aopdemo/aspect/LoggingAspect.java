package aopdemo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Aspect
@Component
@Order(20)
public class LoggingAspect {

    private static Logger myLogger = Logger.getLogger(LoggingAspect.class.getName());


    @Around("execution(* aopdemo.service.TrafficFortuneService.getFortune(..))")
    public Object ComputeTimeForAroundGetFortune(ProceedingJoinPoint pjp) throws Throwable {

        String method = pjp.getSignature().toShortString();
        myLogger.info("\n==>> Executing @Around on method: " + method);

        long begin = System.currentTimeMillis();

        // this will run getFortune method
        Object result = null;

        try {
            result = pjp.proceed();

        } catch (Exception e) {
            // log the exception
            myLogger.warning(e.getMessage());
            // give the user a default message
            result = "Default message - generated because an exception has been thrown";

            // in case we want to send exception to Main:
            throw e;

        }

        long end = System.currentTimeMillis();

        // calculate duration for running getFortune()
        long duration = end - begin;
        myLogger.info("\n==>> Duration: " + duration/1000.0 + " seconds");

        return result;
    }

}
