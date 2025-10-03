package aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AopExpressions {

    @Pointcut("execution(* ro.ccar.aopdemo.dao.*.*(..))")
    public void pointcutForAllMethodsInDaoPackage() { }

    @Pointcut("execution(* ro.ccar.aopdemo.dao.*.get*())")
    public void pointcutForGetters() { }

    @Pointcut("execution(void ro.ccar.aopdemo.dao.*.set*(..))")
    public void pointcutForSetters() { }

    @Pointcut("pointcutForAllMethodsInDaoPackage() && !(pointcutForGetters() || pointcutForSetters())")
    public void excludeGettersAndSetters() { }

    @Pointcut("execution(* ro.ccar.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void pointcutForFindAccounts() { }
}
