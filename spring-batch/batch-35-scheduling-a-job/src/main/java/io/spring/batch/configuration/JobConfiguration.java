package io.spring.batch.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Configuration
@RequiredArgsConstructor
public class JobConfiguration extends DefaultBatchConfigurer implements ApplicationContextAware {

	private final JobBuilderFactory jobBuilderFactory;

	private final StepBuilderFactory stepBuilderFactory;

	private final JobExplorer jobExplorer;

	private final JobRepository jobRepository;

	private final JobRegistry jobRegistry;

	private ApplicationContext applicationContext;

	// custom-made job launcher so that each job will run on it's on thread.
	@Override
	public JobLauncher getJobLauncher() {
		SimpleJobLauncher jobLauncher = null;
		try {
			jobLauncher = new SimpleJobLauncher();
			jobLauncher.setJobRepository(jobRepository);
			jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
			jobLauncher.afterPropertiesSet();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return jobLauncher;
	}

	// This registry is needed when using the job operator method of starting the job:
	// register all the job objects
	@Bean
	public JobRegistryBeanPostProcessor jobRegister() throws Exception {
		JobRegistryBeanPostProcessor registrar = new JobRegistryBeanPostProcessor();

		registrar.setJobRegistry(this.jobRegistry);
		registrar.setBeanFactory(this.applicationContext.getAutowireCapableBeanFactory());
		registrar.afterPropertiesSet();

		return registrar;
	}

	// This job operator is needed when using the job operator method of starting the job:
	// the job operator wraps the job launcher
	@Bean
	public JobOperator jobOperator() throws Exception {
		SimpleJobOperator simpleJobOperator = new SimpleJobOperator();

		simpleJobOperator.setJobLauncher(getJobLauncher());
		simpleJobOperator.setJobParametersConverter(new DefaultJobParametersConverter());
		simpleJobOperator.setJobRepository(this.jobRepository);
		simpleJobOperator.setJobExplorer(this.jobExplorer);
		simpleJobOperator.setJobRegistry(this.jobRegistry);

		simpleJobOperator.afterPropertiesSet();

		return simpleJobOperator;
	}

	@Bean
	@StepScope
	public Tasklet tasklet() {
		return (contribution, chunkContext) -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
			Long instanceId = chunkContext.getStepContext().getJobInstanceId();
			String jobName = chunkContext.getStepContext().getJobName();
			System.out.println(">> Job " + jobName + "(" + instanceId + ") started at " + LocalDateTime.now().format(formatter));
			Thread.sleep(4000L);
			System.out.println("Job will finish....");
			return RepeatStatus.FINISHED;
		};
	}

	// run incrementer is incrementing job parameters to have a unique set for each run
	// in BATCH_JOB_EXECUTION_PARAMS table we will have a column called long_val that will keep the incremented value
	
	// we can use a daily incrementer: 
	//               .incrementer(new DailyJobTimestamper())
	// in this case the value in the  d_val column BATCH_JOB_EXECUTION_PARAMS table will be the timestamp: yyyy-MM-dd HH:mm:ss
	@Bean
	public Job job() {
		return jobBuilderFactory.get("schedulingAJob")
				.incrementer(new RunIdIncrementer())
				.start(stepBuilderFactory.get("step-schedulingAJob")
						.tasklet(tasklet())
						.build())
				.build();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
