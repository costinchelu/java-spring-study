package springjdbc.repository;

import springjdbc.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {

    // custom methods:
    Optional<List<Course>> findByNameEndingWith(String arg);

    Optional<List<Course>> findByNameContaining(String arg);

    @Query(name = "get_some_courses")
    Optional<List<Course>> namedQueryExample();

    @Query(value = "SELECT * FROM course_details WHERE id >= :id", nativeQuery = true)
    Optional<List<Course>> nativeQueryExample(Long id);

    @Query(value = "SELECT c FROM Course c WHERE SIZE(c.students) >= :size")
    Optional<List<Course>> jpqlExample(int size);
}
