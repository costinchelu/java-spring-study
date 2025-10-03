package aopdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import aopdemo.dao.AccountDAO;
import aopdemo.dependency.Account;

import java.util.List;


public class AfterThrowingDemoApp {

    public static void main(String[] args) {

        // read Spring config Java Class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);


        // getting bean from Spring container
        AccountDAO accountD = context.getBean("accountDAO", AccountDAO.class);

        List<Account> theAccounts = null;

        try {
            // add boolean flag to simulate exceptions
            boolean tripWire = true;
            // this will throw an exception
            theAccounts = accountD.findAccounts(tripWire);
        } catch (Exception e) {
            System.out.println("\n\nMain program ... caught exception" + e);
        }

        // the program gets the modified results (if this is the case)
        System.out.println("\n\nMain Program: AfterThrowingDemoApp");
        System.out.println("----");
        System.out.println(theAccounts);
        System.out.println("\n");
        System.out.println("We have an empty list because the method was interrupted by the thrown exception");


        // close the context
        context.close();
    }
}
