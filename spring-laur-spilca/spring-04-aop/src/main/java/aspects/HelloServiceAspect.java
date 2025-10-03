package aspects;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class HelloServiceAspect {

    @Before("execution(* services.HelloService.hello(..))")
    public void before() {
        System.out.println("BEFORE");
    }

    @After("execution(* services.HelloService.hello(..))")
    public void after() {
        System.out.println("AFTER");
    }

    @AfterReturning("execution(* services.HelloService.hello(..))")
    public void afterReturning() {
        System.out.println("AFTER RETURNING");
    }

    @AfterThrowing("execution(* services.HelloService.boo(..))")
    public void afterThrowing() {
        System.out.println("AFTER THROWING");
    }

    @Around("execution(* services.HelloService.hello2(..))")
    public Object around(ProceedingJoinPoint joinPoint) {
        //this aspect will completely swallow the execution of the hello2(String)
        //if we are not using jointPoint parameter (which represents method from execution)
        //then our original hello2 method will never execute
        System.out.println("AROUND (before)");

        Object result = null;
        Object[] params;
        try {
            params = joinPoint.getArgs();
            if (params[0].equals("Michael")) {
                result = joinPoint.proceed(new Object[] {"Mihai"});
            } else {
                result = joinPoint.proceed();
            }
            System.out.println("AROUND (after)");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return result;
    }

    @Before("execution(*  h2rest.services.*.*(..))")
    public void beforeEveryMethod() {
        System.out.println("--------------------");
    }

}

/*
we should try to not change the flow of the program with these aspects
we should use aspects like decorators. Not as replacers of the initial logic.
Or else the code could become unmaintainable.
- transaction ability
- logging
- authentication, authorisation
* */
