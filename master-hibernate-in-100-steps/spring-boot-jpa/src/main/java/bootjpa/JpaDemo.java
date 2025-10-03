package bootjpa;

import bootjpa.jpa.PersonJpaRepository;
import bootjpa.entity.Person;
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
public class JpaDemo implements CommandLineRunner {

    PersonJpaRepository personJpaRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaDemo.class, args);
    }

    // command line runner -> code will lunch up as soon as the application context is ready
    @Override
    public void run(String... args) throws Exception {

        List<Person> people = personJpaRepository.findAll();
        log.info("List of all persons:");
        people.forEach(p -> log.info(p.toString()));

        log.info("Person id 10002: {}", personJpaRepository.findById(10002L));

        personJpaRepository.insertWithQuery(
                Person.builder()
                        .id(10004L)
                        .name("Jean")
                        .location("Paris")
                        .birthDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(831237792), ZoneId.systemDefault()))
                        .build());

        log.info("Inserting 1 row into DB");

        // used for update (provide id)
        personJpaRepository.insertOrUpdate(
                Person.builder()
                        .id(10001L)
                        .name("Clara")
                        .location("Milano")
                        .birthDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(830037392), ZoneId.systemDefault()))
                        .build());
        log.info("1 row is updated");

        // used for insert (no id)
        personJpaRepository.insertOrUpdate(
                Person.builder()
                        .name("Cristiano")
                        .location("Madrid")
                        .birthDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(827035392), ZoneId.systemDefault()))
                        .build());
        log.info("Inserting 1 row into DB");

        personJpaRepository.deleteById(10002L);
        log.info("Deleting Person with id = 10002.");
    }
}

