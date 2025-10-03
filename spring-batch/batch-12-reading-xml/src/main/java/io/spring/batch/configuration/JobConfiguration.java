package io.spring.batch.configuration;

import io.spring.batch.domain.Customer;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


@Configuration
@AllArgsConstructor
public class JobConfiguration {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public ItemReader<Customer> customerItemReader() {

		Jaxb2Marshaller customerMarshaller = new Jaxb2Marshaller();
		customerMarshaller.setClassesToBeBound(Customer.class);

		return new StaxEventItemReaderBuilder<Customer>()
				.name("customerItemReader")
				.resource(new ClassPathResource("data/customers.xml"))
				.addFragmentRootElements("customer")
				.unmarshaller(customerMarshaller)
				.build();
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
		return stepBuilderFactory.get("step1-readingXml")
				.<Customer, Customer>chunk(10)
				.reader(customerItemReader())
				.writer(customerItemWriter())
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("readingXmlJob")
				.start(step1())
				.build();
	}
}
