package jobrunrdemo;

import org.jobrunr.jobs.states.StateName;
import org.jobrunr.storage.StorageProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
class JobEndpointTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    StorageProvider storageProvider;

    @Test
    @DisplayName("Test job enqueued.")
    void givenEndpoint_whenJobEnqueued_thenJobIsProcessedWithin30Seconds() {
        String response = runJobViaRest("from-test");
        assertEquals("Job is enqueued.", response);

        await()
                .atMost(30, TimeUnit.SECONDS)
                .until(() -> storageProvider.getDistinctJobSignatures(StateName.SCHEDULED).size() == 1L);
    }

    @Test
    @DisplayName("Test job scheduled.")
    void givenEndpoint_whenJobScheduled_thenJobIsScheduled() {
        String response = scheduleJobViaRest("from-test", Duration.ofHours(3));
        assertEquals("Job is scheduled.", response);

        await()
                .atMost(30, TimeUnit.SECONDS)
                .until(() -> storageProvider.getDistinctJobSignatures(StateName.SCHEDULED).size() == 1L);
    }

    private String runJobViaRest(String input) {
        return restTemplate.getForObject(
                "http://localhost:8080/run-job?name=" + input,
                String.class);
    }

    private String scheduleJobViaRest(String input, Duration duration) {
        return restTemplate.getForObject(
                "http://localhost:8080/schedule-job?name=" + input + "&when=" + duration.toString(),
                String.class);
    }

}