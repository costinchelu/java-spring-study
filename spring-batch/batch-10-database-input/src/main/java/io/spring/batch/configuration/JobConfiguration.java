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
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	private DataSource dataSource;

// JdbcCursorItemReader is a more simple approach. It is not thread safe! It holds on to the same cursor.
	// ORDER BY clause is very important for Spring Batch. In the event of an error, we need to know
	// when the error occurred and when the job will restart it should start from that point on further.

//	@Bean
//	public JdbcCursorItemReader<Customer> cursorItemReader() {
//		JdbcCursorItemReader<Customer> reader = new JdbcCursorItemReader<>();
//
//		reader.setSql("select id, firstName, lastName, birthdate from customer order by lastName, firstName");
//		reader.setDataSource(this.dataSource);
//		reader.setRowMapper(new CustomerRowMapper());
//
//		return reader;
//	}

	// it reads the DB page by page and that depends on the implementation of each DB type
	@Bean
	public JdbcPagingItemReader<Customer> pagingItemReader() {
		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();

		reader.setDataSource(this.dataSource);
		// typically it is the most performant option to return a page size equal to the chunk size
		reader.setFetchSize(20);
		reader.setRowMapper(new CustomerRowMapper());

		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");

		// the sort key should be a unique key, because contrary to the JdbcCursorItemReader, the tracking of the last
		// read entity is the last sortKey (in case of JdbcCursorItemReader the tracking was the count - no of read entities)
		Map<String, Order> sortKeys = new HashMap<>(1);
		sortKeys.put("id", Order.ASCENDING);
		queryProvider.setSortKeys(sortKeys);
		reader.setQueryProvider(queryProvider);

		return reader;
	}

	@Bean
	public ItemWriter<Customer> customerItemWriter() {
		return items -> {
			for (Customer item : items) {
				System.out.println(item.toString());
			}
		};
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Customer, Customer>chunk(20)
				.reader(pagingItemReader())
				.writer(customerItemWriter())
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("databaseInputJob")
				.start(step1())
				.build();
	}
}
