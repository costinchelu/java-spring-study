package cloudconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Profile({"test", "local"})
public class TestRestController {

    @Value("${app.name: default value if the property cannot be found}")
    private String appName;

    @Value("some static message")
    private String staticMessage;

    @Value("${app.list.values}")
    public List<String> values;

    private final DummySettings dummySettings;

    private final Environment env;

    public TestRestController(DummySettings dummySettings, Environment env) {
        this.dummySettings = dummySettings;
        this.env = env;
    }

    @GetMapping("/test-course")
    public String course() {
        return appName;
    }

    @GetMapping("/test-list")
    public String list() {
        return String.join("<br>", values);
    }

    @GetMapping("/test-config")
    public String config() {
        return dummySettings.getHost() + "/" + dummySettings.getPort() + "/"
                + dummySettings.getConnection();
    }

    @GetMapping("/envdetails")
    public String envDetails() {
        return env.toString();
    }
}
