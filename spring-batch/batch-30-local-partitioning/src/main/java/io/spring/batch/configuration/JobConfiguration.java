package io.spring.batch.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import io.spring.batch.domain.ColumnRangePartitioner;
import io.spring.batch.domain.Customer;
import io.spring.batch.domain.CustomerRowMapper;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/*
Partitioning is dividing into equal parts (that can be executed in parallel)

In a partition step we are configuring 2 steps: master and slave
master = responsible for the division of the data communicating to the slaves what partitions to execute
and aggregating the results of the slaves into a single status for the partition step
slave = configured as any step would be

Partition steps can be restarted and only non-finished partitions will be picked up (benefit over multithreaded step)

Step: [slaveStep-localPartitioningJob:partition3] executed in 8s344ms
Step: [slaveStep-localPartitioningJob:partition1] executed in 8s499ms
Step: [slaveStep-localPartitioningJob:partition2] executed in 8s523ms
Step: [slaveStep-localPartitioningJob:partition0] executed in 8s587ms
Step: [masterStep-localPartitioningJob] executed in 8s648ms
Job: [SimpleJob: [name=localPartitioningJob2]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 8s681ms
 */

@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	private DataSource dataSource;

	private static final int GRID_SIZE = 4;

	// partitioner - understands the data and how to divide it up
	@Bean
	public ColumnRangePartitioner partitioner() {
		ColumnRangePartitioner columnRangePartitioner = new ColumnRangePartitioner();
		// divides a uniformly distributed id column in the db
		// it gets the min and max values and divides the difference by the number of partitions to be created
		columnRangePartitioner.setColumn("id");
		columnRangePartitioner.setDataSource(this.dataSource);
		columnRangePartitioner.setTable("customer");

		return columnRangePartitioner;
	}

	// minValue and maxValue will be passed from the execution context and will be used within the SQL query
	@Bean
	@StepScope
	public JdbcPagingItemReader<Customer> pagingItemReader(
			@Value("#{stepExecutionContext['minValue']}")Long minValue,
			@Value("#{stepExecutionContext['maxValue']}")Long maxValue) {
		System.out.println("reading " + minValue + " to " + maxValue);
		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();

		reader.setDataSource(this.dataSource);
		reader.setFetchSize(1000);
		reader.setRowMapper(new CustomerRowMapper());

		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");
		// query within the range for each partition:
		queryProvider.setWhereClause("where id >= " + minValue + " and id < " + maxValue);

		Map<String, Order> sortKeys = new HashMap<>(1);
		sortKeys.put("id", Order.ASCENDING);
		queryProvider.setSortKeys(sortKeys);
		reader.setQueryProvider(queryProvider);

		return reader;
	}

	@Bean
	@StepScope
	public JdbcBatchItemWriter<Customer> customerItemWriter() {
		JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();

		itemWriter.setDataSource(this.dataSource);
		itemWriter.setSql("INSERT INTO NEW_CUSTOMER VALUES (:id, :firstName, :lastName, :birthdate)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		itemWriter.afterPropertiesSet();

		return itemWriter;
	}

	// we configure 4 partitions (4 threads because we have 4 cores on this machine)
	@Bean
	public Step masterStep() {
		return stepBuilderFactory.get("masterStep-localPartitioningJob")
				.partitioner(slaveStep().getName(), partitioner())
				.step(slaveStep())
				.gridSize(GRID_SIZE)
				.taskExecutor(new SimpleAsyncTaskExecutor())
				.build();
	}

	@Bean
	public Step slaveStep() {
		return stepBuilderFactory.get("slaveStep-localPartitioningJob")
				.<Customer, Customer>chunk(1000)
				.reader(pagingItemReader(null, null))
				.writer(customerItemWriter())
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("localPartitioningJob")
				.start(masterStep())
				.build();
	}
}
