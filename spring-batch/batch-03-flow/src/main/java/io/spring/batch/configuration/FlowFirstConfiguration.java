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
public class FlowFirstConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step myFirstStep() {
		return stepBuilderFactory.get("myFirstStep")
				.tasklet((contribution, chunkContext) -> {
					System.out.println("myFirstStep was executed");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Job flowFirstJob(@Qualifier("foo") Flow flow) {
		return jobBuilderFactory.get("flowFirstJob")
				.start(flow)
				.next(myFirstStep())
				.end()
				.build();
	}

	// the flow will be executed before myFirstStep
}
