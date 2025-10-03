package section10.services;

import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class RandomFortuneService implements FortuneService {

    private String[] data = {
            "First random text",
            "Second random text",
            "Third random text",
            "Fourth random text",
            "Fifth random text"
    };

    Random random = new Random();

    @Override
    public String getFortune() {
        return data[random.nextInt(data.length)];
    }
}
