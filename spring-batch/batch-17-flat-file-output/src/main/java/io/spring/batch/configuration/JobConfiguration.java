package io.spring.batch.configuration;

import java.io.File;

import java.util.HashMap;
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
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	private DataSource dataSource;

	private CustomerLineAggregator customerLineAggregator;

	@Bean
	public JdbcPagingItemReader<Customer> pagingItemReader() {
		JdbcPagingItemReader<Customer> itemReader = new JdbcPagingItemReader<>();

		itemReader.setDataSource(this.dataSource);
		itemReader.setFetchSize(100);
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
	public FlatFileItemWriter<Customer> customerItemWriter() throws Exception {
		FlatFileItemWriter<Customer> itemWriter = new FlatFileItemWriter<>();

		// the PassThroughLineAggregator uses the toString method to create the flat file
//		itemWriter.setLineAggregator(new PassThroughLineAggregator<>());

		// our custom line aggregator will create a json file
		itemWriter.setLineAggregator(customerLineAggregator);
		String customerOutputPath = File.createTempFile("customerOutput", ".json").getAbsolutePath();
		System.out.println(">> File output path: " + customerOutputPath);
		itemWriter.setResource(new FileSystemResource(customerOutputPath));
		itemWriter.afterPropertiesSet();

		return itemWriter;
	}

	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step-flatFileOutputJob")
				.<Customer, Customer>chunk(100)
				.reader(pagingItemReader())
				.writer(customerItemWriter())
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("flatFileOutputJob13")
				.start(step1())
				.build();
	}
}
