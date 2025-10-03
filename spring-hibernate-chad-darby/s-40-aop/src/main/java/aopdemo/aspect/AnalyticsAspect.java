package aopdemo.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;



@Aspect
@Component
@Order(30)
public class AnalyticsAspect {

    @Before("aopdemo.aspect.AopExpressions.excludeGettersAndSetters()")
    public void performApiAnalytics() {
        System.out.println("===>>> EXECUTING - API ANALYTICS");
    }

}
