package io.spring.batch.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import io.spring.batch.domain.Customer;
import io.spring.batch.domain.CustomerRowMapper;

import io.spring.batch.processor.UpperCaseItemProcessor;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	private DataSource dataSource;

	@Bean
	public JdbcPagingItemReader<Customer> pagingItemReader() {
		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();

		reader.setDataSource(this.dataSource);
		reader.setFetchSize(100);
		reader.setRowMapper(new CustomerRowMapper());

		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");

		Map<String, Order> sortKeys = new HashMap<>(1);
		sortKeys.put("id", Order.ASCENDING);

		queryProvider.setSortKeys(sortKeys);

		reader.setQueryProvider(queryProvider);

		return reader;
	}

	@Bean
	public ItemWriter<Customer> xmlItemWriter() throws IOException {
		Jaxb2Marshaller customerMarshaller = new Jaxb2Marshaller();
		customerMarshaller.setClassesToBeBound(Customer.class);

		File customerOutput = File.createTempFile("customerOutput", ".xml");
		System.out.println(">> Output Path: " + customerOutput.getAbsolutePath());

		return new StaxEventItemWriterBuilder<Customer>()
				.name("customerItemWriter")
				.resource(new FileSystemResource(customerOutput.getAbsolutePath()))
				.rootTagName("customers")
				.marshaller(customerMarshaller)
//				.shouldDeleteIfEmpty(true)
				.build();
	}

	@Bean
	public UpperCaseItemProcessor itemProcessor() {
		return new UpperCaseItemProcessor();
	}

	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step-itemProcessorInterfaceJob")
				.<Customer, Customer>chunk(10)
				.reader(pagingItemReader())
				.processor(itemProcessor())
				.writer(xmlItemWriter())
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("itemProcessorInterfaceJob")
				.start(step1())
				.build();
	}
}
