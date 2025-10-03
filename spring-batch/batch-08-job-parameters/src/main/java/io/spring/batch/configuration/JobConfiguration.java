package io.spring.batch.configuration;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job jobParametersJob() {
		return jobBuilderFactory.get("jobParametersJob")
				.start(step1())
				.build();
	}

	// @StepScope - instantiate this object once the step that is using it calls it (lazily instantiated)
	// in the meantime (before this object is instantiated, a proxy will be used to satisfy this dependency)
	// we can pass in job parameters - in this example, one that is called "message"
	// the parameter will be passed on the command line as key-value message=Hello!

	// java -jar target/job-parameters-0.0.1-SNAPSHOT.jar message=Hello!

	// Job: [SimpleJob: [name=jobParametersJob]] launched with the following parameters: [{message=Hello!}]
	// Executing step: [step1]
	//Hello!

	// if the job fails, we can restart it with the same parameters in order to pick up were we left off

	// if the job completes, then we cannot run it again with the same parameters
	// [JobInstanceAlreadyCompleteException: A job instance already exists and is complete for parameters={message=Hello!}.
	// If you want to run this job again, change the parameters.]
	@Bean
	@StepScope
	public Tasklet helloWorldTasklet(@Value("#{jobParameters['message']}") String message) {
		return (stepContribution, chunkContext) -> {
			System.out.println(message);
			return RepeatStatus.FINISHED;
		};
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.tasklet(helloWorldTasklet(null))
				.build();
	}
}
