package springjdbc.repository;

import springjdbc.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentSpringDataRepository extends JpaRepository<Student, Long> {

}
