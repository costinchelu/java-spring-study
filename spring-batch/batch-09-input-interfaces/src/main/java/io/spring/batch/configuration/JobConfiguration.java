package io.spring.batch.configuration;

import java.util.ArrayList;
import java.util.List;

import io.spring.batch.reader.StatelessItemReader;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public StatelessItemReader statelessItemReader() {
		return new StatelessItemReader(List.of("one", "two", "three", "four", "five"));
	}

	// the generics before the chunk method, offers a type safety across the process. So in this case:
	// we are reading a String and passing also a String to the writer
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<String, String>chunk(2)
				.reader(statelessItemReader())
				.writer(list -> {
					for (String curItem : list) {
						System.out.println("currentItem = " + curItem);
					}
				}).build();
	}

	@Bean
	public Job interfacesJob() {
		return jobBuilderFactory.get("interfacesJob")
				.start(step1())
				.build();
	}
}
