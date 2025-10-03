package app;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// By default, Spring boot it loads property from application.properties,
// we can use @PropertySource to load other .properties files.

//@PropertySource("classpath:custom.properties")
@Component
@ConfigurationProperties("app")
@Data
@NoArgsConstructor
public class AppProperties {

    private String error;

    private List<Menu> menus = new ArrayList<>();

    private Compiler compiler = new Compiler();

    @Data
    @NoArgsConstructor
    public static class Menu {

        private String name;

        private String path;

        private String title;
    }

    @Data
    @NoArgsConstructor
    public static class Compiler {

        private String timeout;

        private String outputFolder;
    }
}
