package coachDemo.prototypeScope;

import java.util.Random;

public class HappyFortuneService implements FortuneService {

    private String[] fortunes = {
            "Happy day", "Nice day", "Great day", "Awesome day", "Fantastic day"
    };

    @Override
    public String getFortune() {
        Random random = new Random();
        return fortunes[random.nextInt(fortunes.length)];
    }
}
