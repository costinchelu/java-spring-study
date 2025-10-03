SPRING SECURITY AND THYMELEAF
=============================

A student asked for a Thymeleaf version of the application.

Full solution code is available at this link:


For docs on Spring MVC and Thymeleaf integration, see this link
- https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html

For docs on Spring Security and Thymeleaf integration, see this link
- https://www.thymeleaf.org/doc/articles/springsecurity.html


A couple of high-level changes

1. Add the Thymeleaf pom entries

		<!-- Thymeleaf core support -->
		<dependency>
			<groupId>org.thymeleaf</groupId>
			<artifactId>thymeleaf-spring5</artifactId>
			<version>3.0.11.RELEASE</version>
		</dependency>

		<!-- Thymeleaf security support -->
		<dependency>
			<groupId>org.thymeleaf.extras</groupId>
			<artifactId>thymeleaf-extras-springsecurity5</artifactId>
			<version>3.0.4.RELEASE</version>
		</dependency>

2. Configure Thymeleaf engine and resolvers. 
See code example in: DemoAppConfig.java


3. Create Thymeleaf views
See code examples in: webapp/WEB-INF/views


