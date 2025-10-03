package io.spring.batch.jpaitemwriter.configuration;

import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

public class JpaBatchConfigurer implements BatchConfigurer {

    private DataSource dataSource;

    private EntityManagerFactory entityManagerFactory;

    private JobRepository jobRepository;

    private PlatformTransactionManager transactionManager;

    private JobLauncher jobLauncher;

    private JobExplorer jobExplorer;

    public JpaBatchConfigurer(DataSource dataSource, EntityManagerFactory entityManagerFactory) {
        this.dataSource = dataSource;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JobRepository getJobRepository() {
        return this.jobRepository;
    }

    @Override
    public PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    @Override
    public JobLauncher getJobLauncher() {
        return this.jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() {
        return this.jobExplorer;
    }

    @PostConstruct
    public void initialize() {
        try {
            JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(entityManagerFactory);
            jpaTransactionManager.afterPropertiesSet();
            this.transactionManager = jpaTransactionManager;

            this.jobRepository = createJobRepository();
            this.jobExplorer = createJobExplorer();
            this.jobLauncher = createJobLauncher();

        }
        catch (Exception e) {
            throw new BatchConfigurationException(e);
        }
    }

    private JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(this.jobRepository);
        simpleJobLauncher.afterPropertiesSet();
        return simpleJobLauncher;
    }

    private JobExplorer createJobExplorer() throws Exception {
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(this.dataSource);
        jobExplorerFactoryBean.afterPropertiesSet();
        return jobExplorerFactoryBean.getObject();
    }

    private JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(this.dataSource);
        jobRepositoryFactoryBean.setTransactionManager(this.transactionManager);
        jobRepositoryFactoryBean.afterPropertiesSet();
        return jobRepositoryFactoryBean.getObject();
    }
}
