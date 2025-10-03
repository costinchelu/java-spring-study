package app;

//import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties("global")
@Validated
@Data
public class GlobalProperties {

    //@Value("${global.thread-pool}")
    @Max(5)
    @Min(0)
    private int threadPool;

//    @Value("${global.email}")
    @NotEmpty
    private String email;
}
