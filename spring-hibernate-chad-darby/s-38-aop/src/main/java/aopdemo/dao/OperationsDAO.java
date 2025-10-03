package aopdemo.dao;

import org.springframework.stereotype.Component;


// basic Spring component

@Component
public class OperationsDAO {

    public void addOp() {
        System.out.println(getClass() + ": DOING MY DB WORK: ADDING OPERATIONS");
    }

    public void updateOp(String param) {
        System.out.println(getClass() + ": UPDATE ACCOUNT WITH: " + param);
    }
}
