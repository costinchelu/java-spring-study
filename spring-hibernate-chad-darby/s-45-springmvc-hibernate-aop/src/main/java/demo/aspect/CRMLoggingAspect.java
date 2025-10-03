package demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Aspect
@Component
public class CRMLoggingAspect {

    // setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    // setup pointcut declarations
    @Pointcut("execution(* ro.ccar.controller.*.*(..))")
    private void forControllerPackage() {  }

    @Pointcut("execution(* ro.ccar.service.*.*(..))")
    private void forServicePackage() {  }

    @Pointcut("execution(* ro.ccar.dao.*.*(..))")
    private void forDaoPackage() {  }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {  }

    // add @Before advice
    @Before("forAppFlow()")
    public void before(JoinPoint jp) {

        // display method we are calling
        String method = jp.getSignature().toShortString();
        myLogger.info("====>>>> in @Before: calling method: " + method);

        // display arguments to the method
        Object[] arguments = jp.getArgs();
        for (Object o : arguments) {
            myLogger.info("====>>>> argument: " + o);
        }
    }

    // add @AfterReturning advice
    @AfterReturning(pointcut = "forAppFlow()", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {

        // display method we are calling
        String method = jp.getSignature().toShortString();
        myLogger.info("====>>>> in @AfterReturning: from method: " + method);

        // display data returned
        myLogger.info("====>>>> result: " + result);
    }


}
