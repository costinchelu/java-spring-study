package io.spring.batch.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import io.spring.batch.domain.Customer;
import io.spring.batch.domain.CustomerRowMapper;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/*
For multithreaded step:
 - each chunk will run in its own thread
- restartability is not usable in this case
- the ability to retain the state in a useful way is lost

No multithreading:
Job: [SimpleJob: [name=multithreadedStepJob6]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 30s669ms

With multithreading:
Job: [SimpleJob: [name=multithreadedStepJob4]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 12s737ms
 */
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
		reader.setFetchSize(1000);
		reader.setRowMapper(new CustomerRowMapper());

		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");

		Map<String, Order> sortKeys = new HashMap<>(1);

		sortKeys.put("id", Order.ASCENDING);

		queryProvider.setSortKeys(sortKeys);

		reader.setQueryProvider(queryProvider);
		// this state is useless in the context of multithreading, so it should be set to false:
		reader.setSaveState(false);
		// there is no way that the reader will know which items where read, so there is no way to restart

		return reader;
	}

	// copy contents from customer table to new_customer
	@Bean
	public JdbcBatchItemWriter<Customer> customerItemWriter() {
		JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();

		itemWriter.setDataSource(this.dataSource);
		itemWriter.setSql("INSERT INTO NEW_CUSTOMER VALUES (:id, :firstName, :lastName, :birthdate)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		itemWriter.afterPropertiesSet();

		return itemWriter;
	}

	// SimpleAsyncTaskExecutor is not recommended in the production env.
	// there is no concept like pooling of threads or reusing threads
	// recommended: ThreadPoolTaskExecutor
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step-multithreadedStepJob")
				.<Customer, Customer>chunk(1000)
				.reader(pagingItemReader())
				.writer(customerItemWriter())
				.taskExecutor(new SimpleAsyncTaskExecutor())
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("multithreadedStepJob8")
				.start(step1())
				.build();
	}
}
