package io.spring.batch.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import io.spring.batch.domain.Customer;
import io.spring.batch.domain.CustomerLineAggregator;
import io.spring.batch.domain.CustomerRowMapper;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private StepBuilderFactory stepBuilderFactory;

	private JobBuilderFactory jobBuilderFactory;

	private DataSource dataSource;

	private CustomerLineAggregator customerLineAggregator;

	@Bean
	public JdbcPagingItemReader<Customer> pagingItemReader() {
		JdbcPagingItemReader<Customer> itemReader = new JdbcPagingItemReader<>();

		itemReader.setDataSource(this.dataSource);
		itemReader.setFetchSize(10);
		itemReader.setRowMapper(new CustomerRowMapper());

		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");

		Map<String, Order> sortKeys = new HashMap<>(1);

		sortKeys.put("id", Order.ASCENDING);

		queryProvider.setSortKeys(sortKeys);

		itemReader.setQueryProvider(queryProvider);

		return itemReader;
	}

	@Bean
	public FlatFileItemWriter<Customer> jsonItemWriter() throws Exception {
		FlatFileItemWriter<Customer> itemWriter = new FlatFileItemWriter<>();

		itemWriter.setLineAggregator(customerLineAggregator);
		String customerOutputPath = File.createTempFile("customerOutput", ".json").getAbsolutePath();
		System.out.println(">> Output Path: " + customerOutputPath);
		itemWriter.setResource(new FileSystemResource(customerOutputPath));
		itemWriter.afterPropertiesSet();

		return itemWriter;
	}

	@Bean
	public StaxEventItemWriter<Customer> xmlItemWriter() throws IOException {
		Jaxb2Marshaller customerMarshaller = new Jaxb2Marshaller();
		customerMarshaller.setClassesToBeBound(Customer.class);

		File customerOutput = File.createTempFile("customerOutput", ".xml");
		System.out.println(">> Output Path: " + customerOutput.getAbsolutePath());

		return new StaxEventItemWriterBuilder<Customer>()
				.name("customerItemWriter")
				.resource(new FileSystemResource(customerOutput.getAbsolutePath()))
				.rootTagName("customers")
				.marshaller(customerMarshaller)
				.build();
	}

	// - compositeIW -> will loop through the item list and pass them to the writers
	// we need to comment streams for the delegates in the step builder.
	@Bean
	public CompositeItemWriter<Customer> compositeItemWriter() throws Exception {
		List<ItemWriter<? super Customer>> writers = new ArrayList<>(2);
		writers.add(xmlItemWriter());
		writers.add(jsonItemWriter());

		CompositeItemWriter<Customer> itemWriter = new CompositeItemWriter<>();
		itemWriter.setDelegates(writers);
		itemWriter.afterPropertiesSet();

		return itemWriter;
	}

	// - determines where (based on the item) should be written
	// we also need to register delegates as streams (stream(xmlItemWriter) etc)
	// compositeItemWriter implements ItemStream. It works as a pass-through through the delegate writers for the ItemStream methods
	// because classifierCompositeItemWriter does not implement itemStream we need to register delegates as streams.
	@Bean
	public ClassifierCompositeItemWriter<Customer> classifierItemWriter() throws Exception {
		ClassifierCompositeItemWriter<Customer> itemWriter = new ClassifierCompositeItemWriter<>();

		itemWriter.setClassifier(new CustomerClassifier(xmlItemWriter(), jsonItemWriter()));

		return itemWriter;
	}

	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step-writeMultipleLocJob")
				.<Customer, Customer>chunk(10)
				.reader(pagingItemReader())
//				.writer(compositeItemWriter())
				.writer(classifierItemWriter())
				.stream(xmlItemWriter())
				.stream(jsonItemWriter())
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("writeMultipleLocJob")
				.start(step1())
				.build();
	}
}