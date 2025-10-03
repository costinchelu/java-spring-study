package section10;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import section10.coach.Coach;
import section10.coach.SwimCoach;
import section10.services.DatabaseFortuneService;
import section10.services.FortuneService;


@Configuration
//@ComponentScan("section10")
@PropertySource("classpath:application.properties")
public class SportConfig {

    // define bean for our default service (method name is the BEAN ID)
    @Bean
    public FortuneService databaseFortuneService() {
        return new DatabaseFortuneService();
    }

    // define bean for our coach and inject dependency
    @Bean
    public Coach swimCoach() {
        // we need to call the dependency method
        return new SwimCoach(databaseFortuneService());
    }
}

/*
For property source in Spring 4.2 and lower:

    @Bean
    public static PropertySourcesPlaceholderConfigurer
                    propertySourcesPlaceHolderConfigurer() {

        return new PropertySourcesPlaceholderConfigurer();
    }
 */