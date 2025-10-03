package microserviceconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Profile("default")
public class DefaultRestController {

    @Value("${app.name: default value if the property cannot be found}")
    private String appName;

    @Value("some static message")
    private String staticMessage;

    @Value("${app.list.values}")
    public List<String> values;

    private final DummySettings dummySettings;

    public DefaultRestController(DummySettings dummySettings) {
        this.dummySettings = dummySettings;
    }

    @GetMapping("/course")
    public String course() {
        return appName;
    }

    @GetMapping("/list")
    public String list() {
        return String.join("<br>", values);
    }

    @GetMapping("/config")
    public String config() {
        return dummySettings.getHost() + "/" + dummySettings.getPort() + "/"
                + dummySettings.getConnection();
    }
}
