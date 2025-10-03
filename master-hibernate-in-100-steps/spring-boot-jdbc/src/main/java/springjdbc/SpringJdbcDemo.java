package springjdbc;

import springjdbc.jdbctemplate.SpringJdbcPersonDao;
import springjdbc.model.Person;
import springjdbc.vanillajdbc.VanillaJdbcPersonDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@AllArgsConstructor
@SpringBootApplication
public class SpringJdbcDemo implements CommandLineRunner {

    SpringJdbcPersonDao springJdbcPersonDao;

    VanillaJdbcPersonDao vanillaJdbcPersonDao;

    public static void main(String[] args) {
        SpringApplication.run(SpringJdbcDemo.class, args);
    }

    // command line runner -> code will lunch up as soon as the application context is ready
    @Override
    public void run(String... args) throws Exception {
        List<Person> persons = springJdbcPersonDao.findAll();
        log.info("List of all persons:");
        persons.forEach(p -> log.info(p.toString()));

        log.info("Person id 10002: {}", springJdbcPersonDao.findById(10002));

        List<Person> persons2 = springJdbcPersonDao.findByName(new String[] {"John", "Hans"});
        log.info("List of persons named John or Hans:");
        persons2.forEach(p -> log.info(p.toString()));

        log.info("Deleting Person with id = 10002. Number of rows deleted = {}", springJdbcPersonDao.deleteById(10002));

        log.info("Inserting {} row into DB", springJdbcPersonDao.insertPerson(new Person(10004, "Jean", "Paris",
                LocalDateTime.ofInstant(Instant.ofEpochSecond(831237792), ZoneId.systemDefault()))));

        log.info("{} row is updated", springJdbcPersonDao.updatePerson(Person.builder().id(10001).name("Clara").location("Milano").build()));

        List<Person> persons3 = springJdbcPersonDao.findAllWithCustomRowMapper();
        log.info("List of all persons (using custom row mapper):");
        persons3.forEach(p -> log.info(p.toString()));

        List<Person> persons4 = springJdbcPersonDao.findByNameExtractor(new String[] {"Hans", "Clara"});
        log.info("List of all persons (using resultSet extractor):");
        persons4.forEach(p -> log.info(p.toString()));
    }
}

