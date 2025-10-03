package io.spring.batch.configuration;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class ChildJobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step step1a() {
		return stepBuilderFactory.get("step1a")
				.tasklet((contribution, chunkContext) -> {
					System.out.println("\t>>This is step 1a");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Job childJob() {
		return jobBuilderFactory.get("childJob")
				.start(step1a())
				.build();
	}
}
