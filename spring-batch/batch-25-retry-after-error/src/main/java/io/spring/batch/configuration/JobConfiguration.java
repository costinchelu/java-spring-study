package io.spring.batch.configuration;

import java.util.List;
import java.util.stream.IntStream;

import io.spring.batch.components.CustomRetryableException;
import io.spring.batch.components.RetryItemProcessor;
import io.spring.batch.components.RetryItemWriter;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


/*
we have to run from the command line with arguments in order to cause the retry cases or else everything will go fine

java -jar target/retry-0.0.1-SNAPSHOT.jar -retry=processor

or

java -jar target/retry-0.0.1-SNAPSHOT.jar -retry=writer

on the fifth retry the job will be successful and the process wil continue until the end

Reader is NOT retryable! (forward only contract)
 */
@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Bean
	@StepScope
	public ListItemReader<String> reader() {
		List<String> items = IntStream.rangeClosed(1, 100).boxed().map(String::valueOf).toList();
		System.out.println("Reading is done.");
		return new ListItemReader<>(items);
	}

	@Bean
	@StepScope
	public RetryItemProcessor processor(@Value("#{jobParameters['retry']}")String retry) {
		RetryItemProcessor processor = new RetryItemProcessor();
		processor.setRetry(StringUtils.hasText(retry) && retry.equalsIgnoreCase("processor"));
		return processor;
	}

	@Bean
	@StepScope
	public RetryItemWriter writer(@Value("#{jobParameters['retry']}")String retry) {
		RetryItemWriter writer = new RetryItemWriter();
		writer.setRetry(StringUtils.hasText(retry) && retry.equalsIgnoreCase("writer"));
		return writer;
	}

	// faultTolerant() permits the retry step
	// Only steps that return the specific type of exception will be retried
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step-retryAfterErrorJob")
				.<String, String>chunk(10)
				.reader(reader())
				.processor(processor(null))
				.writer(writer(null))
				.faultTolerant()
				.retry(CustomRetryableException.class)
				.retryLimit(15)
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("retryAfterErrorJob")
				.start(step1())
				.build();
	}
}
