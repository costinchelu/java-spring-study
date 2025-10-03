package io.spring.batch.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import io.spring.batch.domain.Customer;
import io.spring.batch.domain.CustomerLineAggregator;
import io.spring.batch.domain.CustomerRowMapper;

import io.spring.batch.domain.FilteringItemProcessor;
import io.spring.batch.domain.UpperCaseItemProcessor;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
@AllArgsConstructor
public class JobConfiguration {

	public JobBuilderFactory jobBuilderFactory;

	public StepBuilderFactory stepBuilderFactory;

	public DataSource dataSource;

	@Bean
	public JdbcPagingItemReader<Customer> pagingItemReader() {
		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();

		reader.setDataSource(this.dataSource);
		reader.setFetchSize(10);
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
	public FlatFileItemWriter<Customer> customerItemWriter() throws Exception {
		FlatFileItemWriter<Customer> itemWriter = new FlatFileItemWriter<>();

		itemWriter.setLineAggregator(new CustomerLineAggregator());
		String customerOutputPath = File.createTempFile("customerOutput", ".json").getAbsolutePath();
		System.out.println(">> Output Path: " + customerOutputPath);
		itemWriter.setResource(new FileSystemResource(customerOutputPath));
		itemWriter.afterPropertiesSet();

		return itemWriter;
	}

	@Bean
	public CompositeItemProcessor<Customer, Customer> itemProcessor() throws Exception {

		List<ItemProcessor<Customer, Customer>> delegates = new ArrayList<>(2);

		delegates.add(new FilteringItemProcessor());
		delegates.add(new UpperCaseItemProcessor());

		CompositeItemProcessor<Customer, Customer> compositeItemProcessor = new CompositeItemProcessor<>();

		compositeItemProcessor.setDelegates(delegates);
		compositeItemProcessor.afterPropertiesSet();

		return compositeItemProcessor;
	}

	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step-compositeItemProcessor")
				.<Customer, Customer>chunk(10)
				.reader(pagingItemReader())
				.processor(itemProcessor())
				.writer(customerItemWriter())
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("compositeItemProcessor")
				.start(step1())
				.build();
	}
}
