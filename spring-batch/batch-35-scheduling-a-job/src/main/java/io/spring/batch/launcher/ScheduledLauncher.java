package io.spring.batch.launcher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
@AllArgsConstructor
@Slf4j
public class ScheduledLauncher {

	private JobOperator jobOperator;

//	@Scheduled(fixedDelay = 5000L)
// https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions
	@Scheduled(cron = "*/5 * * * * *")
	public void runJob() {
		try {
			this.jobOperator.startNextInstance("schedulingAJob");

		} catch (NoSuchJobException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		} catch (JobParametersNotFoundException | JobParametersInvalidException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (JobRestartException e) {
			log.error(e.getMessage());
		} catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage());
		} catch (UnexpectedJobExecutionException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
		}
	}
}
