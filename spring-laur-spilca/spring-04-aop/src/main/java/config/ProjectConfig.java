package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@ComponentScan(basePackages = {"h2rest.services", "aspects"})
@EnableAspectJAutoProxy
public class ProjectConfig { }
