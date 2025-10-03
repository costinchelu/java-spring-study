package section10.services;

import org.springframework.stereotype.Component;


@Component
public class DatabaseFortuneService implements FortuneService {

    @Override
    public String getFortune() {
        return "Run Database Service...";
    }
}
