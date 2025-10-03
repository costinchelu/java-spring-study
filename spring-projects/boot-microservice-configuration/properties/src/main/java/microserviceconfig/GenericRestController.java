package microserviceconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class GenericRestController {

    @Value("${app.name: default value if the property cannot be found}")
    private String appName;

    @Value("some static message")
    private String staticMessage;

    @Value("${app.list.values}")
    public List<String> values;

    @Value("#{${app.keyValuePair}}")
    private Map<String, String>  keyValue;

    private final DummySettings dummySettings;

    public GenericRestController(DummySettings dummySettings) {
        this.dummySettings = dummySettings;
    }

    @GetMapping("/course")
    public String course() {
        return appName;
    }

    @GetMapping("/list")
    public String list() {
//        return values.stream().map(str -> str.concat("<br>")).reduce("", String::concat);

        // java 8:
//        return values.stream().collect(Collectors.joining("<br>"));

        // java 11:
        return String.join("<br>", values);
    }

    @GetMapping("/map")
    public String maps() {
        return keyValue
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " -> " + entry.getValue())
                .collect(Collectors.joining("<br>"));
    }

    @GetMapping("config")
    public String config() {
        return dummySettings.getHost() + "/" + dummySettings.getPort() + "/"
                + dummySettings.getConnection();
    }
}
