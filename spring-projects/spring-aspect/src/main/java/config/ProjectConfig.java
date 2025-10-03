package config;

import aspects.LoggingAspect;
import aspects.SecurityAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.logging.Logger;

@Configuration
@ComponentScan(basePackages = "h2rest.services, aspects")
@EnableAspectJAutoProxy
public class ProjectConfig {

    private final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Bean
    public LoggingAspect loggingAspect() {
        logger.info("Logging aspect wired in through configuration");
        return new LoggingAspect();
    }

    // @Bean
    // public SecurityAspect securityAspect() {
    //   return new SecurityAspect();
    // }
}
