package aopdemo;

import aopdemo.dao.AccountDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import aopdemo.dependency.Account;

import java.util.List;


public class AfterReturningDemoApp {

    public static void main(String[] args) {

        // read Spring config Java Class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);


        // getting bean from Spring container
        AccountDAO accountD = context.getBean("accountDAO", AccountDAO.class);

        List<Account> theAccounts = accountD.findAccounts();

        // the program gets the modified results (if this is the case)
        System.out.println("\n\nMain Program: AfterReturningDemoApp");
        System.out.println("----");
        System.out.println(theAccounts);
        System.out.println("\n");


        // close the context
        context.close();
    }
}
