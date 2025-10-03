package bootjpa.jpa;

import bootjpa.entity.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PersonJpaRepository {

    @PersistenceContext
    EntityManager entityManager;

    // will use JPQL
    public List<Person> findAll() {
         return entityManager.createNamedQuery("find_all_persons", Person.class).getResultList();
    }

    public Person findById(long id) {
        return entityManager.find(Person.class, id);
    }

    public Person insertOrUpdate(Person person) {
        return entityManager.merge(person);
    }

    public void insertWithQuery(Person person) {
        entityManager.createNativeQuery("INSERT INTO person (id, name, location, birth_date) VALUES (?, ?, ?, ?)")
                .setParameter(1, person.getId())
                .setParameter(2, person.getName())
                .setParameter(3, person.getLocation())
                .setParameter(4, person.getBirthDate())
                .executeUpdate();
    }

    public void deleteById(long id) {
        entityManager.remove(findById(id));
    }

}
