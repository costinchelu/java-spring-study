package neo4jdemo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import neo4jdemo.entity.Person;
import neo4jdemo.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
@EnableNeo4jRepositories
@Slf4j
@AllArgsConstructor
public class DemoNeo4j implements CommandLineRunner {

    private PersonRepository personRepository;

    public static void main(String[] args){
        SpringApplication.run(DemoNeo4j.class, args);
    }

    @Override
    public void run(String... args) {
        Person mihai = new Person("Mihai");
        Person alexandra = new Person("Alexandra");
        Person dan = new Person("Dan");
        Person ioana = new Person("Ioana");
        Person radu = new Person("Radu");
        Person ana = new Person("Ana");
        List<Person> persoane = List.of(mihai, alexandra, dan, ioana, radu, ana);
        personRepository.saveAll(persoane);

        mihai = personRepository.findByName(mihai.getName());
        mihai.worksWith(alexandra);
        mihai.worksWith(dan);
        mihai.worksWith(ioana);
        mihai.worksWith(radu);
        personRepository.save(mihai);

        alexandra = personRepository.findByName(alexandra.getName());
        alexandra.worksWith(dan);
        personRepository.save(alexandra);

        ioana = personRepository.findByName(ioana.getName());
        ioana.worksWith(radu);
        ioana.worksWith(ana);
        personRepository.save(ioana);

        log.info("Colegii lui Dan: {}", getColegi(dan));
        log.info("Colegii Ioanei: {}", getColegi(ioana));
        log.info("Colegii lui Anei: {}", getColegi(ana));
    }

    private String getColegi(Person person) {
        return personRepository.findByTeammatesName(person.getName())
                .stream()
                .map(Person::getName)
                .collect(Collectors.joining(", "));
    }
}
