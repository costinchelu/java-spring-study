package neo4jdemo.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

@Node
@NoArgsConstructor
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "COLEG")
    private Set<Person> teammates;

    public Person(String name) {
        this.name = name;
    }

    public void worksWith(Person person) {
        if (teammates == null) {
            teammates = new HashSet<>();
        }
        teammates.add(person);
    }

    public String toString() {
        return "Colegii angajatului " + this.name + " => "
                + Optional.ofNullable(this.teammates).orElse(Collections.emptySet())
                .stream()
                .map(Person::getName)
                .toList();
    }
}
