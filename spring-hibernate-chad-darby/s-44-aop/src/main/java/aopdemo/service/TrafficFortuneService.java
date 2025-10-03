package aopdemo.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class TrafficFortuneService {

    public String getFortune(boolean tripWire) {

        // simulate a delay
        try {
            if (tripWire) {
                throw new RuntimeException("Ooopsie!");
            }

            TimeUnit.SECONDS.sleep(3);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // return a fortune
        return "Expect heavy traffic this morning";
    }
}
