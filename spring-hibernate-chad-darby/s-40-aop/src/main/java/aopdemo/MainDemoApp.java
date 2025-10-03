package aopdemo;

import aopdemo.dao.AccountDAO;
import aopdemo.dao.MembershipDAO;
import aopdemo.dao.OperationsDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import aopdemo.dependency.Account;

public class MainDemoApp {

    public static void main(String[] args) {

        // read Spring config Java Class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);


        // getting bean from Spring container
        OperationsDAO operationsD = context.getBean("operationsDAO", OperationsDAO.class);
        MembershipDAO membershipD = context.getBean("membershipDAO", MembershipDAO.class);
        AccountDAO accountD = context.getBean("accountDAO", AccountDAO.class);

        Account account = new Account();
        account.setName("John");
        account.setLevel("Platinum");

        // call the business methods
        accountD.addAccount(account);
        accountD.addAccount(account, true);


        // call other business methods
        operationsD.addOp();
        operationsD.updateOp("Some parameter");
        membershipD.addAccount();
        membershipD.goToSleep();


        // call the accountDao getter/setter methods
        accountD.setName("Some name");
        accountD.setServiceCode("Service code ...");
        String accountDName = accountD.getName();
        String accountDServiceCode = accountD.getServiceCode();

        // close the context
        context.close();
    }
}
