package profilesdemo;

import profilesdemo.config.ServerProperties;
import profilesdemo.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class Application implements CommandLineRunner {

    private WeatherService weatherService;

    private ServerProperties serverProperties;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(weatherService.forecast());
        System.out.println(serverProperties);
    }
}
