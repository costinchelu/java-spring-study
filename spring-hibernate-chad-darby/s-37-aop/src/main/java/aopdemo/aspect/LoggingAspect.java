package aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class LoggingAspect {

    // @Pointcut - we can reuse a pointcut declaration (and even share/combine) for using in other methods
    @Pointcut("execution(* ro.ccar.aopdemo.dao.*.*(..))")
    public void pointcutForAllMethodsInDaoPackage() {    }

    // now we can have more aspects applied for the same methods

    @Before("pointcutForAllMethodsInDaoPackage()")
    public void aspectPackageLevel() {
        System.out.println("\n===>>> EXECUTING STUFF AT DAO PACKAGE LEVEL METHODS");
    }

    @Before("pointcutForAllMethodsInDaoPackage()")
    public void performApiAnalytics() {
        System.out.println("===>>> Perform API analytics");
    }


    // we can also combine pointcuts (using logic operators (&& || !))



}
