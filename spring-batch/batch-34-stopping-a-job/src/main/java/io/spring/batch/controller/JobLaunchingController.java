package io.spring.batch.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@AllArgsConstructor
@Slf4j
public class JobLaunchingController {

	private JobOperator jobOperator;

	// curl --data 'name=j1' localhost:8080
	@PostMapping(value = "/")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public long launch(@RequestParam("name") String name) {
		// looks up after a job by its name when it needs to run it
		try {
			return this.jobOperator.start("stoppingAJob", String.format("name=%s", name));

		} catch (JobInstanceAlreadyExistsException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage());
		} catch (NoSuchJobException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		} catch (JobParametersInvalidException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	// curl -X DELETE localhost:8080/28
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void stop(@PathVariable("id") Long id) {
		try {
			this.jobOperator.stop(id);

		} catch (NoSuchJobExecutionException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		} catch (JobExecutionNotRunningException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
