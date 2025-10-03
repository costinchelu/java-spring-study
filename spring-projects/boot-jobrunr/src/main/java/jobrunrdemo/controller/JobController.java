package jobrunrdemo.controller;

import jobrunrdemo.service.JobService;
import lombok.AllArgsConstructor;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@AllArgsConstructor
public class JobController {

    private JobScheduler jobScheduler;

    private JobService jobService;

    @GetMapping("/run-job")
    public String runJob(@RequestParam(value = "name", defaultValue = "Hello World") String name) {
        jobScheduler.enqueue(() -> jobService.execute(name));
        return "Job is enqueued.";
    }

    @GetMapping("/schedule-job")
    public String scheduleJob(
            @RequestParam(value = "name", defaultValue = "Hello World") String name,
            @RequestParam(value = "when", defaultValue = "PT3H") String when) {

        jobScheduler.schedule(
                Instant.now().plus(Duration.parse(when)),
                () -> jobService.execute(name)
        );

        return "Job is scheduled.";
    }
}
