package profilesdemo.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("server")
@Data
@NoArgsConstructor
public class ServerProperties {

    private String email;

    private List<Cluster> cluster = new ArrayList<>();

    @Data
    @NoArgsConstructor
    public static class Cluster {

        private String ip;

        private String path;
    }
}
