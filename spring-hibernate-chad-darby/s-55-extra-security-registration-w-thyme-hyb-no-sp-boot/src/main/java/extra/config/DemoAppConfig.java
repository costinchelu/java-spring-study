package extra.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.luv2code.springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties")
public class DemoAppConfig implements WebMvcConfigurer {

	// set up variable to hold the properties
	@Autowired
	private Environment env;

	@Autowired
	private ApplicationContext applicationContext;

	// set up a logger for diagnostics
	private Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * Create the Thymeleaf template resolver
	 * 
	 * @return
	 */
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(applicationContext);
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		return templateResolver;
	}

	/**
	 * In order to use the thymeleaf-extras-springsecurity, add the SpringSecurity
	 * dialect to our Template Engine so that we can use the sec:* attributes and
	 * special expression utility objects.
	 * 
	 * See:
	 * https://github.com/thymeleaf/thymeleaf-extras-springsecurity#configuration
	 * 
	 * @return
	 */
	@Bean
	public SpringSecurityDialect securityDialect() {
		SpringSecurityDialect securityDialect = new SpringSecurityDialect();
		return securityDialect;
	}

	/**
	 * Create the Thymeleaf template engine
	 * 
	 * @return
	 */
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		templateEngine.addDialect(securityDialect());
		return templateEngine;
	}

	/**
	 * Configure the Thymeleaf view resolver
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		registry.viewResolver(resolver);
	}

	// define a bean for our security datasource

	@Bean
	public DataSource securityDataSource() {

		// create connection pool
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

		// set the jdbc driver
		try {
			securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}

		// for sanity's sake, let's log url and user ... just to make sure we are
		// reading the data
		logger.info("jdbc.url=" + env.getProperty("jdbc.url"));
		logger.info("jdbc.user=" + env.getProperty("jdbc.user"));

		// set database connection props
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));

		// set connection pool props
		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));

		securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));

		securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));

		securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

		return securityDataSource;
	}

	// need a helper method
	// read environment property and convert to int

	private int getIntProperty(String propName) {

		String propVal = env.getProperty(propName);

		// now convert to int
		int intPropVal = Integer.parseInt(propVal);

		return intPropVal;
	}

	private Properties getHibernateProperties() {

		// set hibernate properties
		Properties props = new Properties();

		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

		return props;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {

		// create session factorys
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		// set the properties
		sessionFactory.setDataSource(securityDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());

		return sessionFactory;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

}
