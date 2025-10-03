package aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;



@Aspect
@Component
@Order(20)
public class LoggingAspect {

//    @Before("pointcutForAllMethodsInDaoPackage()")
//    public void aspectPackageLevel() {
//        System.out.println("\n===>>> EXECUTING STUFF AT DAO PACKAGE LEVEL METHODS");
//    }

    @Before("aopdemo.aspect.AopExpressions.excludeGettersAndSetters()")
    public void performLogging() {
        System.out.println("===>>> EXECUTING - LOGGING");
    }

}
