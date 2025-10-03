package aopdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import aopdemo.dependency.Account;


@Aspect
@Component
@Order(20)
public class LoggingAspect {

    @Before("aopdemo.aspect.AopExpressions.pointcutForAllMethodsInDaoPackage()")
    public void aspectPackageLevel(JoinPoint jp) {
        System.out.println("===>>> EXECUTING - LOGGING");


        // display the method signature
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        System.out.println("=> METHOD: " + methodSignature);


        // display method arguments
        Object[] arguments = jp.getArgs();

        for (Object argument : arguments) {
            System.out.println(argument);

            if (argument instanceof Account) {
                // downcast and print Account specific stuff
               Account account = (Account) argument;
                System.out.println("account name: " + account.getName());
                System.out.println("account level: " + account.getLevel());
            }
        }

    }

//    @Before("ro.ccar.aopdemo.aspect.AopExpressions.excludeGettersAndSetters()")
//    public void performLogging(JoinPoint jp) {
//        System.out.println("===>>> EXECUTING - LOGGING");
//    }

}
