package ro.ls.lesson16.actuator.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Component;


@Component
public class MyCustomHealthIndicator implements HealthIndicator {

    @Autowired
    private MetricsEndpoint metricsEndpoint;

    @Override
    public Health health() {
        return Health.up()
                .withDetail("some key", "some value")
                .build();
    }
}
