package io.spring.batch.configuration;

import java.util.List;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public ListItemReader<String> itemReader() {
		List<String> items = IntStream.rangeClosed(1, 112).boxed().map(String::valueOf).toList();
		return new ListItemReader<>(items);
	}

	@Bean
	public SysOutItemWriter itemWriter() {
		return new SysOutItemWriter();
	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("step-itemWriterInterfaceJob")
				.<String, String>chunk(10)
				.reader(itemReader())
				.writer(itemWriter())
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("itemWriterInterfaceJob")
				.start(step())
				.build();
	}
}
