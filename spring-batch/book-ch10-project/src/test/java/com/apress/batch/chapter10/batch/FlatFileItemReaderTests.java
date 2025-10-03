package com.apress.batch.chapter10.batch;

import com.apress.batch.chapter10.configuration.ImportJobConfiguration;
import com.apress.batch.chapter10.domain.CustomerAddressUpdate;
import com.apress.batch.chapter10.domain.CustomerContactUpdate;
import com.apress.batch.chapter10.domain.CustomerNameUpdate;
import com.apress.batch.chapter10.domain.CustomerUpdate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBatchTest
@ContextConfiguration(classes = {ImportJobConfiguration.class, CustomerItemValidator.class, AccountItemProcessor.class})
@EnableBatchProcessing
@JdbcTest
@Disabled
class FlatFileItemReaderTests {

	@Autowired
	private FlatFileItemReader<CustomerUpdate> customerUpdateItemReader;

	public StepExecution getStepExecution() {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("customerUpdateFile", "classpath:customerFile.csv")
				.toJobParameters();

		return MetaDataInstanceFactory.createStepExecution(jobParameters);
	}

	@Test
	void typeConversionTest() throws Exception {
		this.customerUpdateItemReader.open(new ExecutionContext());

		assertTrue(this.customerUpdateItemReader.read() instanceof CustomerAddressUpdate);
		assertTrue(this.customerUpdateItemReader.read() instanceof CustomerContactUpdate);
		assertTrue(this.customerUpdateItemReader.read() instanceof CustomerNameUpdate);
	}
}
