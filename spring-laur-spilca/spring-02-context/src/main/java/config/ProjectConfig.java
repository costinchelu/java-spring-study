package config;

import beans.Cat;
import beans.Owner;
import beans.Store;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@ComponentScan(basePackages = "beans")
public class ProjectConfig {

    @Bean
    public Cat cat() {
        Cat c = new Cat();
        c.setName("Tom");
        return c;
    }

    // calling cat() method will inject the created cat from the Context (not calling the actual cat() method directly)
    // so we will always get the same instance from the context)
    @Bean
    @Primary
    public Owner owner() {
        Owner o = new Owner();
        o.setCat(cat());
        return o;
    }

    // automatic autowiring without using @Autowire annotation
    @Bean
    public Owner owner2(Cat cat) {
        Owner o = new Owner();
        o.setCat(cat);
        return o;
    }

    @Bean
    @Qualifier("gucci")
    public Store gucciStore() {
        Store store = new Store();
        store.setName("Gucci");
        return store;
    }

    @Bean
    @Qualifier("armani")
    public Store armaniStore() {
        Store store = new Store();
        store.setName("Armani");
        return store;
    }
}
