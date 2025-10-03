package springjdbc;

import springjdbc.entity.FullTimeEmployee;
import springjdbc.entity.PartTimeEmployee;
import springjdbc.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
@EntityScan("com.java.study.entity")
@SpringBootApplication
public class InheritanceDemo implements CommandLineRunner {

    private EmployeeRepository employeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(InheritanceDemo.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        employeeRepository.insert(new FullTimeEmployee("Jack", new BigDecimal("10000")));
        employeeRepository.insert(new PartTimeEmployee("Jill", new BigDecimal("50")));

        log.info("All employees: {}", employeeRepository.retrieveAllEmployees());
    }
}

