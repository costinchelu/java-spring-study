package profilesdemo.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class RainingDayService implements WeatherService {

    @Override
    public String forecast() {
        return "Today is raining day!";
    }

}
