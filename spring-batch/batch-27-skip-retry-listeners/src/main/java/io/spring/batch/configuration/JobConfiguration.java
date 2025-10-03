package io.spring.batch.configuration;

import java.util.List;
import java.util.stream.IntStream;

import io.spring.batch.components.CustomSkipException;
import io.spring.batch.components.CustomSkipListener;
import io.spring.batch.components.SkipItemProcessor;
import io.spring.batch.components.SkipItemWriter;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
	public SkipItemProcessor processor() {
		return new SkipItemProcessor();
	}

	@Bean
	@StepScope
	public SkipItemWriter writer() {
		return new SkipItemWriter();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step-skipRetryListenersJob")
				.<String, String>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.faultTolerant()
				.skip(CustomSkipException.class)
				.skipLimit(15)
				.listener(new CustomSkipListener())
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("skipRetryListenersJob")
				.start(step1())
				.build();
	}
}
