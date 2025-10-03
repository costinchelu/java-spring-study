package neo4jdemo.repository;

import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import neo4jdemo.entity.Person;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Person findByName(String name);

    List<Person> findByTeammatesName(String name);
}
