package io.spring.batch.configuration;

import java.util.List;

import io.spring.batch.listener.ChunkListener;
import io.spring.batch.listener.JobListener;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;


@Configuration
@AllArgsConstructor
public class ListenerJobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	// this will be a chunk based step (working with a Reader and a Writer)
	@Bean
	public ItemReader<String> reader() {
		// takes a list and every time we call read on this reader, it will return the next item on that list
		return new ListItemReader<>(List.of("one", "two", "three"));
	}

	@Bean
	public ItemWriter<String> writer() {
		// the writer will just loop over the items we get from the reader
		return new ItemWriter<String>() {
			@Override
			public void write(List<? extends String> items) throws Exception {
				for (String item : items) {
					System.out.println("Writing item " + item);
				}
			}
		};
	}

	// we have 3 items in the reader, and we will use a chunk of 2, so we get more than one chunk
	// (3/2 -> will be broken up into 2 chunks)
	// we need to use faultTolerant in order to use AnnotationBased Step
	// then we add our  listener (ChunkListener)
	// then we run the reader and writer operations
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step-listenerJob")
				.<String, String>chunk(2)
				.faultTolerant()
				.listener(new ChunkListener())
				.reader(reader())
				.writer(writer())
				.build();
	}

	@Bean
	public Job listenerJob(JavaMailSender javaMailSender) {
		return jobBuilderFactory.get("listenerJob")
				.start(step1())
				.listener(new JobListener(javaMailSender))
				.build();
	}
}
