package io.spring.batch.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import io.spring.batch.domain.Customer;
import io.spring.batch.domain.CustomerRowMapper;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/*
Performing complex computation
Communicating with external services for processing

Parallelize processing using an asyncProcessor is useful in these cases when processing is complex and could benefit the parallelization
This will be used in combination with an async writer.
asyncProcessor is using a future


With asyncItemProcessorJob:
Job: [SimpleJob: [name=asyncItemProcessorJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 59s824ms

No async:
Job: [SimpleJob: [name=asyncItemProcessorJob2]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 9m45s436ms

so with the async the job was finished 9 time faster. But in case the processing must be done in a certain order, async cannot be used.
Asynchronous processing and writing will not keep the order.

 */
@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	private DataSource dataSource;

	private static final Random RANDOM = new Random();

	@Bean
	public JdbcPagingItemReader<Customer> pagingItemReader() {
		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();

		reader.setDataSource(this.dataSource);
		reader.setFetchSize(1000);
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
	public ItemProcessor<Customer, Customer> itemProcessor() {
		return item -> {
			// we do a Thread.sleep to simulate a complex processing
			// simulates a processing of up to 10 ms for each item
			Thread.sleep(RANDOM.nextInt(10));
			return new Customer(item.id(),
					item.firstName().toUpperCase(),
					item.lastName().toUpperCase(),
					item.birthdate());
		};
	}

	// SimpleAsyncTaskExecutor is creating a new thread for each tas, so it is not suitable for production
	// there we can use ThreadPoolTaskExecutor
	@Bean
	@SuppressWarnings("rawtypes")
	public AsyncItemProcessor asyncItemProcessor() throws Exception {
		AsyncItemProcessor<Customer, Customer> asyncItemProcessor = new AsyncItemProcessor<>();
		asyncItemProcessor.setDelegate(itemProcessor());
		asyncItemProcessor.setTaskExecutor(new SimpleAsyncTaskExecutor());
		asyncItemProcessor.afterPropertiesSet();
		return asyncItemProcessor;
	}

	@Bean
	public JdbcBatchItemWriter<Customer> customerItemWriter() {
		JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();
		itemWriter.setDataSource(this.dataSource);
		itemWriter.setSql("INSERT INTO NEW_CUSTOMER VALUES (:id, :firstName, :lastName, :birthdate)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		itemWriter.afterPropertiesSet();
		return itemWriter;
	}

	// AsyncItemWriter is what we need to work in conjunction with the AsyncItemProcessor
	@Bean
	public AsyncItemWriter<Customer> asyncItemWriter() throws Exception {
		AsyncItemWriter<Customer> asyncItemWriter = new AsyncItemWriter<>();
		asyncItemWriter.setDelegate(customerItemWriter());
		asyncItemWriter.afterPropertiesSet();
		return asyncItemWriter;
	}

	@Bean
	@SuppressWarnings("unchecked")
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step-asyncItemProcessorJob")
				.<Customer, Customer>chunk(1000)
				.reader(pagingItemReader())
//				.processor(itemProcessor())
//				.writer(customerItemWriter())
				.processor(asyncItemProcessor())
				.writer(asyncItemWriter())
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("asyncItemProcessorJob2")
				.start(step1())
				.build();
	}
}
