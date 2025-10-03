package app;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

// By default, Spring boot it loads property from application.properties,
// we can use @PropertySource to load other .properties files.

@PropertySource("classpath:custom.properties")
@ConfigurationProperties("custom")
@Component
@Data
@NoArgsConstructor
public class CustomProperties {

    @Max(5)
    @Min(0)
    private int threadPool;

    @NotEmpty
    private String email;
}
