package io.spring.batch.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


// USING THE JOB OPERATOR:
@RestController
@AllArgsConstructor
@Slf4j
public class JobLaunchingController {

	private JobOperator jobOperator;

	private static final String EX_412 = "An identical job instance already exists";

	private static final String EX_406 = "No such job";

	private static final String EX_400 = "Invalid job parameters";

	@PostMapping(value = "/")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void launch(@RequestParam("name") String name) {
		// looks up after a job by its name when it needs to run it
		try {
			this.jobOperator.start("startingAJob", String.format("name=%s", name));
		} catch (JobInstanceAlreadyExistsException e) {
			log.error(EX_412);
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, EX_412);
		} catch (NoSuchJobException e) {
			log.error(EX_406);
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, EX_406);
		} catch (JobParametersInvalidException e) {
			log.error(EX_400);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EX_400);
		}
	}
}

/*
USING THE JOB LAUNCHER:

@RestController
@AllArgsConstructor
public class JobLaunchingController {

	private JobLauncher jobLauncher;

	private Job job;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void launch(@RequestParam("name") String name) throws Exception {
  		JobParameters jobParameters =
  				new JobParametersBuilder()
  						.addString("name", name)
  						.toJobParameters();
  		this.jobLauncher.run(job, jobParameters);
	}
}

 */