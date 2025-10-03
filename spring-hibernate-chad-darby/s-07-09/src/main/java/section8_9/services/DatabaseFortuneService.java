package section8_9.services;

import org.springframework.stereotype.Component;


@Component
public class DatabaseFortuneService implements FortuneService {

    @Override
    public String getFortune() {
        return "DatabaseService selected";
    }
}
