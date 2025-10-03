package io.spring.batch.configuration;

import java.util.List;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Bean
	@StepScope
	public StatefulItemReader itemReader() {
		List<String> items = IntStream.rangeClosed(1, 100).boxed().map(String::valueOf).toList();
		return new StatefulItemReader(items);
	}

	@Bean
	public ItemWriter<String> itemWriter() {
		return items -> {
			for (String item : items) {
				System.out.println(">> " + item);
			}
		};
	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("step1-statefulJob")
				.<String, String>chunk(10)
				.reader(itemReader())
				.writer(itemWriter())
				.stream(itemReader())
				.build();
	}

	@Bean
	public Job statefulJob() {
		return jobBuilderFactory.get("statefulJob")
				.start(step())
				.build();
	}
}
