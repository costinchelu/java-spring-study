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
public class StepTranstionConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.tasklet((contribution, chunkContext) -> {
					System.out.println(">> This is step 1");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2")
				.tasklet((contribution, chunkContext) -> {
					System.out.println(">> This is step 2");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Step step3() {
		return stepBuilderFactory.get("step3")
				.tasklet((contribution, chunkContext) -> {
					System.out.println(">> This is step 3");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Job transitionJobSimpleNext() {
		return jobBuilderFactory.get("transitionJobNext")
				.start(step1())
				.on("COMPLETED").to(step2())
				.from(step2()).on("COMPLETED").stopAndRestart(step3())
				.from(step3()).end()
				.build();
	}
	// step3 will not run (it will just stop)
	// next time we run the job, only step3 will run
	// third time, it will complete without running any step (because all of them are COMPLETED)
}
