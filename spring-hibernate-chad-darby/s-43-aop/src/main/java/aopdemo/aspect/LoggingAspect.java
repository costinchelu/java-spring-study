package aopdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import aopdemo.dependency.Account;

import java.util.List;


@Aspect
@Component
@Order(20)
public class LoggingAspect {

    @Before("aopdemo.aspect.AopExpressions.pointcutForAllMethodsInDaoPackage()")
    public void aspectPackageLevel(JoinPoint jp) {
        System.out.println("===>>> EXECUTING @Before - LOGGING");


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

    // new Advice for @AfterReturning on findAccounts()
    @AfterReturning(
            pointcut = "aopdemo.aspect.AopExpressions.pointcutForFindAccounts()",
            returning = "result"
    )
    public void afterReturningFindAccountsAdvice(JoinPoint jp, List<Account> result) {

        // print out which method we are advising on
        String method = jp.getSignature().toShortString();
        System.out.println("\n===>>> Executing @AfterReturning on method: " + method);

        // print out the results of the method call
        System.out.println("\n===>>> Result is: " + result);

        // Now we can modify the returning data:
        // in this case we will convert the account names to uppercase
        convertAccountNamesToUperCase(result);
    }

    private void convertAccountNamesToUperCase(List<Account> result) {
        for (Account account : result) {
            String upperName = account.getName().toUpperCase();
            account.setName(upperName);
        }
    }

    // case of exception
    @AfterThrowing(
            pointcut = "execution(* aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "theException"
    )
    private void afterThrowingFindAccountsAdvice(
            JoinPoint jp, Throwable theException) {

        String method = jp.getSignature().toShortString();
        System.out.println("===>>> EXECUTING @AfterThrowing on method: " + method);

        System.out.println("\n===>>> The Exception is: " + theException);
    }


    // After finally runs regardless of the result
    @After("execution(* aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint jp) {
        String method = jp.getSignature().toShortString();
        System.out.println("===>>> EXECUTING @After (finally) on method: " + method);
    }

}
