package io.spring.batch.domain;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import lombok.Setter;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;


public class ColumnRangePartitioner implements Partitioner {

	private JdbcOperations jdbcTemplate;

	// setter - The name of the SQL table the data are in.
	@Setter
	private String table;

	// setter - The name of the column to partition.
	@Setter
	private String column;

	/**
	 * The data source for connecting to the database.
	 *
	 * @param dataSource a {@link DataSource}
	 */
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Partition a database table assuming that the data in the column specified
	 * are uniformly distributed. The execution context values will have keys
	 * <code>minValue</code> and <code>maxValue</code> specifying the range of
	 * values to consider in each partition. In our case <code>minValue</code>
	 * and <code>maxValue</code> are <code>firstId</code> and <code>lastId</code>
	 *
	 * @see Partitioner#partition(int)
	 */
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		int firstId = jdbcTemplate.queryForObject("SELECT MIN(" + column + ") from " + table, Integer.class);
		int lastId = jdbcTemplate.queryForObject("SELECT MAX(" + column + ") from " + table, Integer.class);
		int targetSize = (lastId - firstId) / gridSize + 1;

		Map<String, ExecutionContext> result = new HashMap<>();
		int number = 0;
		int start = firstId;
		int end = start + targetSize - 1;

		while (start <= lastId) {
			ExecutionContext executionContext = new ExecutionContext();
			result.put("partition" + number, executionContext);

			if (end >= lastId) {
				end = lastId;
			}
			executionContext.putInt("minValue", start);
			executionContext.putInt("maxValue", end);
			start += targetSize;
			end += targetSize;
			number++;
		}

		return result;
	}
}
