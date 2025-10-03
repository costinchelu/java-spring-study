package insertsdemo.dao.repository;


import insertsdemo.dao.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentJPARepository extends JpaRepository<Student, Integer> {
}
