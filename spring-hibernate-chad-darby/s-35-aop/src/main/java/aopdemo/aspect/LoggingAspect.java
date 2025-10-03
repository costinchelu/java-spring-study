package aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class LoggingAspect {

    // we are using pointcut -> a predicate expression for where advice should be applied

    // @Before advice (by not specifying a class name we will match aspect to all methods with addAccount name, and has no params)
    // we can use * for match all
//    @Before("execution(public void addAccount())")
//    public void beforeAddAccountAnyClass() {
//        System.out.println("\n====>>>> EXECUTING @Before ADVICE ON addAccount()");
//    }

    // pointcut for addAccount method of OperationsDAO.class only (regardless of the return type)
    // we need to use the fully qualified class name

//    @Before("execution(* ro.ccar.aopdemo.dao.OperationsDAO.addOp())")
//    public void beforeAddOpOperationsDAOClass() {
//        System.out.println("\n====>>>> EXECUTING @Before ADVICE ON addAccount()");
//    }


    // match with any method that starts with add (modifier is optional):
    @Before("execution(void add*())")
    public void beforeMatchWithAnyMethodStartingWithAdd() {
        System.out.println("\n====>>>> EXECUTING @Before ADVICE ON add*()");
    }

    // match any method starting with update, that has one parameter type String
    @Before("execution(void update*(String))")
    public void beforeWithParameter() {
        System.out.println("\n====>>>> EXECUTING @Before ADVICE ON update*(String)");
    }


    // matching for parameters could be:
    // * -> any parameter
    // (..) any number of parameters
    // * wildcard can match also package names like ro.ccar.aopdemo.dao.*.* which means
    // it matches any class and any method from dao package


    @Before("execution(* add*(aopdemo.dependency.Account))")
    public void beforeAddAccountAdvice() {
        System.out.println("\n====>>>> EXECUTING @Before ADVICE ON add*(Account)");
    }

    @Before("execution(* add*(aopdemo.dependency.Account, ..))")
    public void beforeAddAccountAdviceAnyNumber() {
        System.out.println("\n====>>>> EXECUTING @Before ADVICE ON add*(Account, ..)");
    }

    // match any method with any number of parameters from the dao package
    // (it will run for any method from that package (6 times))
    @Before("execution(* ro.ccar.aopdemo.dao.*.*(..))")
    public void aspectPackageLevel() {
        System.out.println("\n===>>> EXECUTING STUFF AT PACKAGE LEVEL METHODS");
    }
}
