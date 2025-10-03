package jobrunrdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JobService {

    @Job(name = "The sample job without variable")
    public void execute() {
        execute("Hello world!");
    }

    @Job(name = "The sample job with variable %0")
    public void execute(String input) {
        log.info("The sample job has begun. The variable you passed is {}", input);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error("Error while executing sample job {}", e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            log.info("Sample job has finished...");
        }
    }

}