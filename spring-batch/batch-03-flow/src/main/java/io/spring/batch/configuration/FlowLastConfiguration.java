package io.spring.batch.configuration;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class FlowLastConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step myLastStep() {
		return stepBuilderFactory.get("myLastStep")
				.tasklet((contribution, chunkContext) -> {
					System.out.println("myLastStep was executed");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Job flowLastJob(@Qualifier("bar") Flow flow) {
		return jobBuilderFactory.get("flowLastJob")
				.start(myLastStep())
				.on("COMPLETED").to(flow)
				.end()
				.build();
	}

	// the flow will be executed after myLastStep
}
