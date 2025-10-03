package ro.ls.lesson16.actuator.endpoints;


import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "myCustomEndpoint")
public class MyCustomEndpoint {

    @ReadOperation
    public String test() {
        return ":)";
    }
}
