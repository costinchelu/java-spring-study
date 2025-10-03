package ro.ls.lesson16.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {

    @Autowired
    private HealthEndpoint healthEndpoint;

    @GetMapping("/demo")
    public String demo() {
        return healthEndpoint.health().toString();
    }
}
