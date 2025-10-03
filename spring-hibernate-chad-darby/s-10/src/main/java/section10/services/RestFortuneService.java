package section10.services;

import org.springframework.stereotype.Component;


@Component
public class RestFortuneService implements FortuneService {

    @Override
    public String getFortune() {
        return "RESTful service selected";
    }
}
