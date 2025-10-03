
[https://logging.apache.org/log4j/2.x/manual/index.html](https://logging.apache.org/log4j/2.x/manual/index.html)

For getting the logging settings from **log4j.xml**:

set the log4j.xml location in the VM options:

```commandline
-Dlog.dir=./logs -Dlogging.config=./src/main/resources/log4j2.xml
```

Also for Spring Boot we need to point lo log4j (not Logback which is default):

**pom.xml** should have:

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>
</dependencies>
```

sample log4j.xml:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
<!--            https://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/PatternLayout.html -->
<!--            sets the logging pattern:Configuration -> Appenders -> Console -> PatternLayout  -->
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Logger name="com.foo.Bar" level="trace">
            <AppenderRef ref="Console"/>
        </Logger>
<!--        sets the logging level: Loggers -> Root tag -->
        <Root level="info">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```


Second option would be **Log4j2.properties**:

Usually present in the resources folder. If it is created in another location, add this to VM options:

```commandline
-Dlog4j2.configurationFile=file:/folder1/folder2/log4j2.properties
```

Example Log4j2.properties for file logging:

```properties
status = warn
name= RollingFileLogConfigDemo

# Log files location
property.basePath = c:/temp/logs

# RollingFileAppender name, pattern, path and rollover policy
appender.rolling.type = RollingFile
appender.rolling.name = fileLogger
appender.rolling.fileName= ${basePath}/app.log
appender.rolling.filePattern= ${basePath}/app_%d{yyyyMMdd}.log.gz
appender.rolling.layout.type = PatternLayout
appender.rolling.layout.pattern = %d{yyyy-MM-dd HH:mm:ss.SSS} %level [%t] [%l] - %msg%n
appender.rolling.policies.type = Policies

# RollingFileAppender rotation policy
appender.rolling.policies.size.type = SizeBasedTriggeringPolicy
appender.rolling.policies.size.size = 10MB
appender.rolling.policies.time.type = TimeBasedTriggeringPolicy
appender.rolling.policies.time.interval = 1
appender.rolling.policies.time.modulate = true
appender.rolling.strategy.type = DefaultRolloverStrategy
appender.rolling.strategy.delete.type = Delete
appender.rolling.strategy.delete.basePath = ${basePath}
appender.rolling.strategy.delete.maxDepth = 10
appender.rolling.strategy.delete.ifLastModified.type = IfLastModified

# Delete all files older than 30 days
appender.rolling.strategy.delete.ifLastModified.age = 30d

# Configure root logger
rootLogger.level = debug
rootLogger.appenderRef.rolling.ref = fileLogger
```


Example Log4j2.properties for console logging:

```properties
# Extra logging related to initialization of Log4j
# Set to debug or trace if log4j initialization is failing
status = warn
# Name of the configuration
name = ConsoleLogConfigDemo

# Console appender configuration
appender.console.type = Console
appender.console.name = consoleLogger
appender.console.layout.type = PatternLayout
appender.console.layout.pattern = %d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

# Root logger level
rootLogger.level = debug
# Root logger referring to console appender
rootLogger.appenderRef.stdout.ref = consoleLogger
```