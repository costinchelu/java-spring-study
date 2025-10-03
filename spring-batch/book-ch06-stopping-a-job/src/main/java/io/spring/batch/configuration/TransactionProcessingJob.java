package io.spring.batch.configuration;

import io.spring.batch.domain.AccountSummary;
import io.spring.batch.domain.Transaction;
import io.spring.batch.domain.dao.TransactionDao;
import io.spring.batch.domain.dao.TransactionDaoSupport;
import io.spring.batch.processes.TransactionApplierProcessor;
import io.spring.batch.processes.TransactionReader;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.PassThroughFieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

/**
 * 339599,2018-01-13 18:53:29,884.16<br>
 * 991085,2018-01-13 18:53:29,-15.05<br>
 * 297579,2018-01-13 18:53:29,-344.38<br>
 */
@Configuration
@AllArgsConstructor
public class TransactionProcessingJob {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public TransactionDao transactionDao(DataSource dataSource) {
        return new TransactionDaoSupport(dataSource);
    }

    @Bean
    public TransactionApplierProcessor transactionApplierProcessor() {
        return new TransactionApplierProcessor(transactionDao(null));
    }

    //	The first Step = import our file into the database.

    // This is the custom ItemReader
    @Bean
    @StepScope
    public TransactionReader transactionReader() {
        return new TransactionReader(fileItemReader(null));
    }

    // StepScope = obtain a new instance for each step (reader used in 2 steps)
    @Bean
    @StepScope
    public FlatFileItemReader<FieldSet> fileItemReader(@Value("#{jobParameters['transactionFile']}") Resource inputFile) {
        return new FlatFileItemReaderBuilder<FieldSet>()
                .name("fileItemReader")
                .resource(inputFile)
                .lineTokenizer(new DelimitedLineTokenizer())
                .fieldSetMapper(new PassThroughFieldSetMapper())
                .build();
    }

    // the way we will write the values to the database
    @Bean
    public JdbcBatchItemWriter<Transaction> transactionWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Transaction>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO transaction (account_summary_id, timestamp, amount) " +
                        "VALUES (" +
                        "(SELECT id FROM account_summary WHERE account_number = :accountNumber), " +
                        ":timestamp, " +
                        ":amount)")
                .dataSource(dataSource)
                .build();
    }

    /**
     * the definition of the Step itself.
     * <br>
     * Using the StepBuilderFactory, we get a builder and configure it to be a chunk-based Step with the transaction reader
     * and JDBC writer we just configured.
     * We configure this Step to be re-runnable if the Job is restarted.
     * The reason for this is that if the file we import is invalid (meaning the number of records does not match the footer
     * record), we’d want to clear out this import and rerun it with a valid file.
     * <br>
     * After configuring the ability to rerun this Step, we register our TransactionReader as a listener before building our Step.
     */
    @Bean
    public Step importTransactionFileStep() {
        return this.stepBuilderFactory.get("importTransactionFileStep")
                .<Transaction, Transaction>chunk(100)
                .reader(transactionReader())
                .writer(transactionWriter(null))
                .allowStartIfComplete(true)
                .listener(transactionReader())
                .build();
    }


//	The second Step = apply the transactions that were found in the file to the accounts.

    // read the AccountSummary records from the database
    @Bean
    @StepScope
    public JdbcCursorItemReader<AccountSummary> accountSummaryReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<AccountSummary>()
                .name("accountSummaryReader")
                .dataSource(dataSource)
                .sql("SELECT account_number, current_balance FROM account_summary a " +
                        "WHERE a.id IN (" +
                        "SELECT DISTINCT t.account_summary_id FROM transaction t) " +
                        "ORDER BY a.account_number")
                .rowMapper((resultSet, rowNumber) -> {
                    AccountSummary summary = new AccountSummary();
                    summary.setAccountNumber(resultSet.getString("account_number"));
                    summary.setCurrentBalance(resultSet.getDouble("current_balance"));
                    return summary;
                })
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<AccountSummary> accountSummaryWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<AccountSummary>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(
                        new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("UPDATE ACCOUNT_SUMMARY " +
                        "SET CURRENT_BALANCE = :currentBalance " +
                        "WHERE ACCOUNT_NUMBER = :accountNumber")
                .build();
    }

    @Bean
    public Step applyTransactionsStep() {
        return this.stepBuilderFactory.get("applyTransactionsStep")
                .<AccountSummary, AccountSummary>chunk(100)
                .reader(accountSummaryReader(null))
                .processor(transactionApplierProcessor())
                .writer(accountSummaryWriter(null))
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<AccountSummary> accountSummaryFileWriter(@Value("#{jobParameters['summaryFile']}") Resource summaryFile) {
        DelimitedLineAggregator<AccountSummary> lineAggregator = new DelimitedLineAggregator<>();
        BeanWrapperFieldExtractor<AccountSummary> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"accountNumber", "currentBalance"});
        fieldExtractor.afterPropertiesSet();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return new FlatFileItemWriterBuilder<AccountSummary>()
                .name("accountSummaryFileWriter")
                .resource(summaryFile)
                .lineAggregator(lineAggregator)
                .build();
    }

    @Bean
    public Step generateAccountSummaryStep() {
        return this.stepBuilderFactory.get("generateAccountSummaryStep")
                .<AccountSummary, AccountSummary>chunk(100)
                .reader(accountSummaryReader(null))
                .writer(accountSummaryFileWriter(null))
                .build();
    }

    // by moving the check of the record count from the afterStep(StepExecution execution)
    // to the read() method), the configuration becomes cleaner:
    @Bean
    public Job transactionJob() {
        return this.jobBuilderFactory.get("transactionJob3")
                .start(importTransactionFileStep())
                .next(applyTransactionsStep())
                .next(generateAccountSummaryStep())
                .build();

        // Initial configuration
//		        return this.jobBuilderFactory.get("transactionJob")
//				.start(importTransactionFileStep())
//				.on("STOPPED").stopAndRestart(importTransactionFileStep())
//				.from(importTransactionFileStep()).on("*").to(applyTransactionsStep())
//				.from(applyTransactionsStep())
//				.next(generateAccountSummaryStep())
//				.end()
//				.build();
    }
}
