package io.spring.batch.configuration;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.integration.launch.JobLaunchingMessageHandler;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;


@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	private JobLauncher jobLauncher;

	@Bean
	@ServiceActivator(inputChannel = "requests", outputChannel = "replies")
	public JobLaunchingMessageHandler jobLaunchingMessageHandler() {
		return new JobLaunchingMessageHandler(this.jobLauncher);
	}

	// we can use Redit or RabitMQ but here we are using a direct channel
	// input channel - a channel to send requests to
	@Bean
	public DirectChannel requests() {
		return new DirectChannel();
	}

	// what are we listening to back
	@Bean
	public DirectChannel replies() {
		return new DirectChannel();
	}

	@Bean
	@StepScope
	public Tasklet tasklet(@Value("#{jobParameters['name']}") String name) {
		return (contribution, chunkContext) -> {
			System.out.println(">> The job ran for " + name);
			return RepeatStatus.FINISHED;
		};
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("jobLaunchedViaMessages")
				.start(stepBuilderFactory.get("step-jobLaunchedViaMessages")
					.tasklet(tasklet(null))
					.build())
				.build();
	}
}
