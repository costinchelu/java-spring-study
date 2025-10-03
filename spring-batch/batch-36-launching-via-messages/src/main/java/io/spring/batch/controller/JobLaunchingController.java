package io.spring.batch.controller;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class JobLaunchingController {

	private MessageChannel requests;

	private DirectChannel replies;

	private Job job;

	// curl --data 'name=messageJob' localhost:8080
	@PostMapping(value = "/")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void launch(@RequestParam("name") String name) {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("name", name)
				.toJobParameters();

		JobLaunchRequest launchRequest = new JobLaunchRequest(this.job, jobParameters);

		replies.subscribe(message -> {
			JobExecution payload = (JobExecution) message.getPayload();
			System.out.println(">> " + payload.getJobInstance().getJobName() + " resulted in " + payload.getStatus());
		});

		requests.send(MessageBuilder.withPayload(launchRequest).setReplyChannel(replies).build());
	}
}
