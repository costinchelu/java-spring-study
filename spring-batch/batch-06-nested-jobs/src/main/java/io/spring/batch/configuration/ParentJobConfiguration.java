package io.spring.batch.configuration;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@AllArgsConstructor
public class ParentJobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	private Job childJob;

	private JobLauncher jobLauncher;

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.tasklet((contribution, chunkContext) -> {
					System.out.println(">> This is step 1 (parentJob)");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Job parentJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		Step childJobStep = new JobStepBuilder(new StepBuilder("childJobStep"))
				.job(childJob)
				.launcher(jobLauncher)
				.repository(jobRepository)
				.transactionManager(transactionManager)
				.build();

		return jobBuilderFactory.get("parentJob")
				.start(step1())
				.next(childJobStep)
				.build();
	}
}
