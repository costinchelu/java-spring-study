package io.spring.batch.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class JobConfiguration implements ApplicationContextAware {

	private final JobBuilderFactory jobBuilderFactory;

	private final StepBuilderFactory stepBuilderFactory;

	private final JobExplorer jobExplorer;

	private final JobRepository jobRepository;

	private final JobRegistry jobRegistry;

	private final JobLauncher jobLauncher;

	private ApplicationContext applicationContext;

	// This registry is needed when using the job operator method of starting the job:
	// register all the job objects
	@Bean
	public JobRegistryBeanPostProcessor jobRegistrar() throws Exception {
		JobRegistryBeanPostProcessor registry = new JobRegistryBeanPostProcessor();

		registry.setJobRegistry(this.jobRegistry);
		registry.setBeanFactory(this.applicationContext.getAutowireCapableBeanFactory());
		registry.afterPropertiesSet();

		return registry;
	}

	// This job operator is needed when using the job operator method of starting the job:
	// the job operator wraps the job launcher
	@Bean
	public JobOperator jobOperator() throws Exception {
		SimpleJobOperator simpleJobOperator = new SimpleJobOperator();

		simpleJobOperator.setJobLauncher(this.jobLauncher);
		simpleJobOperator.setJobParametersConverter(new DefaultJobParametersConverter());
		simpleJobOperator.setJobRepository(this.jobRepository);
		simpleJobOperator.setJobExplorer(this.jobExplorer);
		simpleJobOperator.setJobRegistry(this.jobRegistry);
		simpleJobOperator.afterPropertiesSet();

		return simpleJobOperator;
	}

	@Bean
	@StepScope
	public Tasklet tasklet(@Value("#{jobParameters['name']}") String name) {
		return (contribution, chunkContext) -> {
			System.out.println("The job ran for %s" + name);
			return RepeatStatus.FINISHED;
		};
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("startingAJob")
				.start(stepBuilderFactory.get("step-startingAJob")
					.tasklet(tasklet(null))
					.build())
				.build();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
