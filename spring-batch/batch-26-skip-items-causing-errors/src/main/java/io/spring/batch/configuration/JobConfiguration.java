package io.spring.batch.configuration;

import java.util.List;
import java.util.stream.IntStream;

import io.spring.batch.components.CustomSkipableException;
import io.spring.batch.components.SkipItemProcessor;
import io.spring.batch.components.SkipItemWriter;

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

java -jar target/retry-0.0.1-SNAPSHOT.jar -skip=processor

or

java -jar target/retry-0.0.1-SNAPSHOT.jar -skip=writer

Once the writer fails, for that chunk, the dimensions will be reduced from 10 to 1.
So the processing/writing will be redone for one item at a time.
Reader is NOT skipable! (forward only contract)
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
	public SkipItemProcessor processor(@Value("#{jobParameters['skip']}")String skip) {
		SkipItemProcessor processor = new SkipItemProcessor();
		processor.setSkip(StringUtils.hasText(skip) && skip.equalsIgnoreCase("processor"));
		return processor;
	}

	@Bean
	@StepScope
	public SkipItemWriter writer(@Value("#{jobParameters['skip']}")String skip) {
		SkipItemWriter writer = new SkipItemWriter();
		writer.setSkip(StringUtils.hasText(skip) && skip.equalsIgnoreCase("writer"));
		return writer;
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step-skippingAfterErrorJob")
				.<String, String>chunk(10)
				.reader(reader())
				.processor(processor(null))
				.writer(writer(null))
				.faultTolerant()
				.skip(CustomSkipableException.class)
				.skipLimit(15)
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("skippingAfterErrorJob")
				.start(step1())
				.build();
	}
}
