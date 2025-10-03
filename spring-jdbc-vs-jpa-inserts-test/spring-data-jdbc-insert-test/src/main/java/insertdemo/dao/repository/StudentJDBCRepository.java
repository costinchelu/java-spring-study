package insertdemo.dao.repository;


import insertdemo.dao.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentJDBCRepository extends CrudRepository<Student, Integer> {
}
