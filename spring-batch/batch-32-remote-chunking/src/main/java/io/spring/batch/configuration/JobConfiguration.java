package io.spring.batch.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import io.spring.batch.domain.Customer;
import io.spring.batch.domain.CustomerRowMapper;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/*
Where Processing = bottleneck (and we cannot scale the input) - remoteChunking
 Input is read by the master
 items themselves are sent to slaves for processing
 the result can be returned to the master for writing or written by the slaves themselves

 In remote partitioning only the description of the data is sent from master to the slaves. The slaves managed the reading
 of the input data themselves.
 In remote chunking the master does the reading and sends the data to the slaves


1 slave process:
Job: [SimpleJob: [name=remoteChunkingJob101111]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 3m20s901ms

5 slave processes:
Job: [SimpleJob: [name=remoteChunkingJob1011111]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 1m41s350ms
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

		return reader;
	}

	@Bean
	public ItemProcessor<Customer, Customer> upperCaseItemProcessor() {
		return item -> new Customer(item.id(),
				item.firstName().toUpperCase(),
				item.lastName().toUpperCase(),
				item.birthdate());
	}

	@Bean(value = "customerItemWriter")
	public JdbcBatchItemWriter<Customer> customerItemWriter() {
		JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();

		itemWriter.setDataSource(this.dataSource);
		itemWriter.setSql("INSERT INTO NEW_CUSTOMER VALUES (:id, :firstName, :lastName, :birthdate)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		itemWriter.afterPropertiesSet();

		return itemWriter;
	}

	@Bean
	public TaskletStep step1() {
		return stepBuilderFactory.get("step-remoteChunkingJob")
				.<Customer, Customer>chunk(1000)
				.reader(pagingItemReader())
				.processor(upperCaseItemProcessor())
				.writer(customerItemWriter())
				.build();
	}

	// the job will be picked up only in the master instance
	@Bean
	@Profile("master")
	public Job job() {
		return jobBuilderFactory.get("remoteChunkingJob1011111")
				.start(step1())
				.build();
	}
}
