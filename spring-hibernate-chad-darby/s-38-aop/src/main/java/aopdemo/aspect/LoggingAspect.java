package aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class LoggingAspect {

    // pointcut for all methods
    @Pointcut("execution(* ro.ccar.aopdemo.dao.*.*(..))")
    public void pointcutForAllMethodsInDaoPackage() {    }


    @Before("pointcutForAllMethodsInDaoPackage()")
    public void aspectPackageLevel() {
        System.out.println("\n===>>> EXECUTING STUFF AT DAO PACKAGE LEVEL METHODS");
    }


    // we can also combine pointcuts (using logic operators (&& || !))


    // pointcut for getters
    @Pointcut("execution(* ro.ccar.aopdemo.dao.*.get*())")
    public void pointcutForGetters() { }

    // pointcut for setters
    @Pointcut("execution(void ro.ccar.aopdemo.dao.*.set*(..))")
    public void pointcutForSetters() { }

    // pointcut combined and using logic operators to exclude getters and setters
    @Pointcut("pointcutForAllMethodsInDaoPackage() && !(pointcutForGetters() || pointcutForSetters())")
    public void excludeGettersAndSetters() { }

    // next we will exclude all getters and setters using conditional pointcut declaration:
    @Before("excludeGettersAndSetters()")
    public void performApiAnalytics() {
        System.out.println("===>>> EXECUTING - METHOD THAT EXCLUDES GETTERS AND SETTERS");
    }

}
