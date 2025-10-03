package io.spring.batch.compositeprocessor.configuration;

import io.spring.batch.compositeprocessor.domain.Customer;
import io.spring.batch.compositeprocessor.service.CaseService;
import io.spring.batch.compositeprocessor.validator.UniqueLastNameValidator;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemProcessorAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.Arrays;

/**
 * Spring Batch allows you to maintain that same division of responsibilities within your business logic
 * by chaining ItemProcessors within a step. Composition can be used to allow you to do more complex
 * orchestration in the ItemProcessor phase of a step.
 * CompositeItemProcessor serves as a wrapper for multiple ItemProcessors, calling them in order.
 */
@Configuration
@AllArgsConstructor
public class CompositeProcessing {

    public JobBuilderFactory jobBuilderFactory;

    public StepBuilderFactory stepBuilderFactory;

    public CaseService service;

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> customerItemReader(@Value("#{jobParameters['customerFile']}") Resource inputFile) {

        return new FlatFileItemReaderBuilder<Customer>()
                .name("customerItemReader")
                .delimited()
                .names("firstName",
                        "middleInitial",
                        "lastName",
                        "address",
                        "city",
                        "state",
                        "zip")
                .targetType(Customer.class)
                .resource(inputFile)
                .build();
    }

    @Bean
    public ItemWriter<Customer> itemWriter() {
        return items -> items.forEach(System.out::println);
    }

    @Bean
    public UniqueLastNameValidator validator() {
        UniqueLastNameValidator uniqueLastNameValidator = new UniqueLastNameValidator();
        uniqueLastNameValidator.setName("validator");
        return uniqueLastNameValidator;
    }

    @Bean
    public ValidatingItemProcessor<Customer> customerValidatingItemProcessor() {
        ValidatingItemProcessor<Customer> itemProcessor = new ValidatingItemProcessor<>(validator());
        itemProcessor.setFilter(true);
        return itemProcessor;
    }

    @Bean
    public ItemProcessorAdapter<Customer, Customer> upperCaseItemProcessor() {
        ItemProcessorAdapter<Customer, Customer> adapter = new ItemProcessorAdapter<>();
        adapter.setTargetObject(service);
        adapter.setTargetMethod("upperCase");
        return adapter;
    }

    @Bean
    @StepScope
    public ItemProcessorAdapter<Customer, Customer> lowerCaseItemProcessor() {
        ItemProcessorAdapter<Customer, Customer> adapter = new ItemProcessorAdapter<>();
        adapter.setTargetObject(service);
        adapter.setTargetMethod("lowerCase");
        return adapter;
    }

    @Bean
    public CompositeItemProcessor<Customer, Customer> itemProcessor() {
        CompositeItemProcessor<Customer, Customer> itemProcessor = new CompositeItemProcessor<>();

        itemProcessor.setDelegates(Arrays.asList(
                customerValidatingItemProcessor(),
                upperCaseItemProcessor(),
                lowerCaseItemProcessor()));

        return itemProcessor;
    }

    @Bean
    public Step copyFileStep() {

        return this.stepBuilderFactory.get("copyFileStep")
                .<Customer, Customer>chunk(5)
                .reader(customerItemReader(null))
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("compositeProcessJob")
                .start(copyFileStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
