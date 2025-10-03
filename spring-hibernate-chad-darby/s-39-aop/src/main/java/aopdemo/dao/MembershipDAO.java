package aopdemo.dao;


import org.springframework.stereotype.Component;

@Component
public class MembershipDAO {

    public void addAccount() {
        System.out.println(getClass() + ": ADDING A MEMBERSHIP ACCOUNT");
    }

    public void goToSleep() {
        System.out.println(getClass() + ": GO TO SLEEP");
    }
}
