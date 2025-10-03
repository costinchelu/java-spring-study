package io.spring.batch.configuration;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.gateway.GatewayProxyFactoryBean;
import org.springframework.integration.stream.CharacterStreamWritingMessageHandler;

import java.util.List;
import java.util.stream.IntStream;


@Configuration
@AllArgsConstructor
public class JobConfiguration implements ApplicationContextAware{

	private final JobBuilderFactory jobBuilderFactory;

	private final StepBuilderFactory stepBuilderFactory;

	private ApplicationContext applicationContext;

	@Bean
	public ListItemReader<String> itemReader() {
		List<String> items = IntStream.range(0, 1000).boxed().map(String::valueOf).toList();
		return new ListItemReader<>(items);
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
	public Step step1() {
		return stepBuilderFactory.get("step-infoMessagesJob")
				.<String, String>chunk(100)
				.reader(itemReader())
				.writer(itemWriter())
				.listener((ChunkListener) chunkListener())
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("infoMessagesJob")
				.start(step1())
				.listener((JobExecutionListener) jobExecutionListener())
				.build();
	}

	@Bean
	public Object jobExecutionListener() {
		GatewayProxyFactoryBean proxyFactoryBean = new GatewayProxyFactoryBean(JobExecutionListener.class);
		proxyFactoryBean.setDefaultRequestChannel(events());
		proxyFactoryBean.setBeanFactory(this.applicationContext);
		return proxyFactoryBean.getObject();
	}

	@Bean
	public Object chunkListener() {
		GatewayProxyFactoryBean proxyFactoryBean = new GatewayProxyFactoryBean(ChunkListener.class);
		proxyFactoryBean.setDefaultRequestChannel(events());
		proxyFactoryBean.setBeanFactory(this.applicationContext);
		return proxyFactoryBean.getObject();
	}

	@Bean
	public DirectChannel events() {
		return new DirectChannel();
	}

	@Bean
	@ServiceActivator(inputChannel = "events")
	public CharacterStreamWritingMessageHandler logger() {
		return CharacterStreamWritingMessageHandler.stdout();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
