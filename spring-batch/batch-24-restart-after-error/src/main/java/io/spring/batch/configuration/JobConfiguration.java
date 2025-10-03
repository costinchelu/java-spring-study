package io.spring.batch.configuration;

import java.util.Map;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	// @StepScope = it means each step will have its own instance (even though they are running the same tasklet)
	@Bean
	@StepScope
	public Tasklet restartTasklet() {
		return (contribution, chunkContext) -> {
			Map<String, Object> stepExecutionContext = chunkContext.getStepContext().getStepExecutionContext();

			if(stepExecutionContext.containsKey("ran")) {
				System.out.println(chunkContext.getStepContext().getStepName() + ">> This time we'll let it go.");
				return RepeatStatus.FINISHED;
			}
			else {
				System.out.println(chunkContext.getStepContext().getStepName() + ">> I don't think so...");
				chunkContext.getStepContext().getStepExecution().getExecutionContext().put("ran", true);
				throw new RuntimeException("Not this time...");
			}
		};
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1-restartAfterErrorJob")
				.tasklet(restartTasklet())
				.build();
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2-restartAfterErrorJob")
				.tasklet(restartTasklet())
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("restartAfterErrorJob")
				.start(step1())
				.next(step2())
				.build();
	}
}
