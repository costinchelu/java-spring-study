package io.spring.batch.configuration;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class BatchConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step startStep() {
		return stepBuilderFactory.get("startStep")
				.tasklet((contribution, chunkContext) -> {
					System.out.println("This is the start tasklet");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Step evenStep() {
		return stepBuilderFactory.get("evenStep")
				.tasklet((contribution, chunkContext) -> {
					System.out.println("This is the even tasklet");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Step oddStep() {
		return stepBuilderFactory.get("oddStep")
				.tasklet((contribution, chunkContext) -> {
					System.out.println("This is the odd tasklet");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("decisionJob3")
				.start(startStep())
				.next(decider())
				.from(oddStep()).on("STOP").end()
				.from(evenStep()).on("STOP").end()
				.from(decider()).on("ODD").to(oddStep())
				.from(decider()).on("EVEN").to(evenStep())
				.from(oddStep()).on("*").to(decider())
				.from(evenStep()).on("*").to(decider())
//				.from(decider()).on("ODD").to(oddStep())
//				.from(decider()).on("EVEN").to(evenStep())
				.end()
				.build();
		// stop condition was inserted first. It will run odd and even steps until it receives a STOP status
	}
	// decider is executed just as a state instead of as real step.
	// There is no jobRepository entry for a decision step (on restart it will be re-executed)
	// (no historical record)

	@Bean
	public JobExecutionDecider decider() {
		return new OddDecider();
	}

	public static class OddDecider implements JobExecutionDecider {

		private int count = 0;

		@Override
		public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
			count++;
			if (count > 5) {
				return new FlowExecutionStatus("STOP");
			}
			// run odd and even steps 5 times
			if (count % 2 == 0) {
				return new FlowExecutionStatus("EVEN");
			} else {
				return new FlowExecutionStatus("ODD");
			}
		}
	}
}
