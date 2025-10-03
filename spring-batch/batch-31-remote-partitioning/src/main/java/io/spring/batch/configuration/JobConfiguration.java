
package io.spring.batch.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import io.spring.batch.domain.ColumnRangePartitioner;
import io.spring.batch.domain.Customer;
import io.spring.batch.domain.CustomerRowMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.integration.partition.BeanFactoryStepLocator;
import org.springframework.batch.integration.partition.MessageChannelPartitionHandler;
import org.springframework.batch.integration.partition.StepExecutionRequestHandler;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.support.PeriodicTrigger;

/*
I/O bound Jobs would be a good fit for this parallelization type

first we need to start rabbitmq:
docker run --name rabmq1 --hostname hostrabbit -d -p 15672:15672 -p 5671:5671 -p 5672:5672 -v v1rabbit:/var/lib/rabbitmq rabbitmq:3-management

management console username and password will be guest



we have to run the service in the terminal:

for each of the required slaves. Open 2-3 powershell terminal windows and run slave instances:
java -jar "-Dspring.profiles.active=slave" target/remote-partitioning-0.0.1-SNAPSHOT.jar

Open another one for the master and the job will be picked up by the slaves:
java -jar "-Dspring.profiles.active=master" target/remote-partitioning-0.0.1-SNAPSHOT.jar


remote partitioning is fully restartable.
low overhead of communication between the master and the slave (master is sending only id ranges to the slaves)

C:\Users\CostinC2\.jdks\corretto-17.0.4.1\bin\java -jar "-Dspring.profiles.active=slave" .\target\remote-partitioning-0.0.1-SNAPSHOT.jar

SLAVE 1:
reading 25501 to 51000
Step: [slaveStep-remotePartitioningJob:partition1] executed in 12s18ms
reading 1 to 25500
Step: [slaveStep-remotePartitioningJob:partition0] executed in 8s425ms

SLAVE 2:
reading 51001 to 76500
Step: [slaveStep-remotePartitioningJob:partition2] executed in 12s260ms

SLAVE 3:
reading 76501 to 102000
Step: [slaveStep-remotePartitioningJob:partition3] executed in 11s45ms

MASTER:
Step: [masterStep-remotePartitioningJob] executed in 25s415ms
Job: [SimpleJob: [name=remotePartitioningJob2]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 25s483ms
 */
@Configuration
@RequiredArgsConstructor
public class JobConfiguration implements ApplicationContextAware {

	public final JobBuilderFactory jobBuilderFactory;

	public final StepBuilderFactory stepBuilderFactory;

	public final DataSource dataSource;

	public final JobExplorer jobExplorer;

	public final JobRepository jobRepository;

	private ApplicationContext applicationContext;

	private static final int GRID_SIZE = 4;

	// Instead of running the partitions locally with threads, we will run them remotely with JVMs
	@Bean
	public PartitionHandler partitionHandler(MessagingTemplate messagingTemplate) throws Exception {
		// MessageChannelPartitionHandler sends messages to the slave steps:
		MessageChannelPartitionHandler partitionHandler = new MessageChannelPartitionHandler();
		partitionHandler.setStepName("slaveStep");
		partitionHandler.setGridSize(GRID_SIZE);
		partitionHandler.setMessagingOperations(messagingTemplate);
		// we can pool the job repository to see if that all the slave executions are in a terminal state
		// if they are, we know that the slave work is done, and we can continue on
		partitionHandler.setPollInterval(5000L);
		partitionHandler.setJobExplorer(this.jobExplorer);
		partitionHandler.afterPropertiesSet();
		return partitionHandler;
	}

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

	// in a new JVM we are only launching and independent step (not an entire job)
	// every message that comes in through "inboundRequests" input channel is being executed
	// once that is down it is sending a response back on the outputChannel
	@Bean
	@Profile("slave")
	@ServiceActivator(inputChannel = "inboundRequests", outputChannel = "outboundStaging")
	public StepExecutionRequestHandler stepExecutionRequestHandler() {
		StepExecutionRequestHandler stepExecutionRequestHandler = new StepExecutionRequestHandler();

		BeanFactoryStepLocator stepLocator = new BeanFactoryStepLocator();
		stepLocator.setBeanFactory(this.applicationContext);
		stepExecutionRequestHandler.setStepLocator(stepLocator);
		stepExecutionRequestHandler.setJobExplorer(this.jobExplorer);

		return stepExecutionRequestHandler;
	}

	@Bean(value = PollerMetadata.DEFAULT_POLLER)
	public PollerMetadata defaultPoller() {
		PollerMetadata pollerMetadata = new PollerMetadata();
		pollerMetadata.setTrigger(new PeriodicTrigger(10));
		return pollerMetadata;
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
		queryProvider.setWhereClause("where id >= " + minValue + " and id <= " + maxValue);

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

	@Bean
	public Step masterStep() throws Exception {
		return stepBuilderFactory.get("masterStep-remotePartitioningJob")
				.partitioner(slaveStep().getName(), partitioner())
				.step(slaveStep())
				.partitionHandler(partitionHandler(null))
				.build();
	}

	@Bean(value = "slaveStep")
	public Step slaveStep() {
		return stepBuilderFactory.get("slaveStep-remotePartitioningJob")
				.<Customer, Customer>chunk(1000)
				.reader(pagingItemReader(null, null))
				.writer(customerItemWriter())
				.build();
	}

	@Bean
	@Profile("master")
	public Job job() throws Exception {
		return jobBuilderFactory.get("remotePartitioningJob2")
				.start(masterStep())
				.build();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
