package config;

import beans.MyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 *
 * Configuration class
 * @Configuration is telling Spring that this class is used for configuration of beans
 * We can have multiple configuration classes
 * also we can have configuration classes together with XML config files
 *
 * @Bean is telling Spring to place that bean in context
 * @Primary is telling Spring that that bean is the default implementation for that class
 * @ComponentScan is telling Spring to search for Components in certain packages. Components will create beans
 *
 */
@Configuration
@ComponentScan(basePackages = {"beans", "autowire"})
public class ProjectConfig {

    @Bean
    @Primary
    public MyBean myBean1() {
        MyBean b = new MyBean();
        b.setText("Hello");
        return b;
    }

    @Bean
    public MyBean myBean2() {
        MyBean b = new MyBean();
        b.setText("Hello 2");
        return b;
    }

    @Bean(name = "B3")
    public MyBean myBean3() {
        MyBean b = new MyBean();
        b.setText("Hello 3");
        return b;
    }

}
