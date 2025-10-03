package demo.dao;

import demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /* no need to specify any methods (Spring will take care to generate implementations)

    With Spring Data REST we don't need to implement services or rest controllers. For many sql operations,
    Spring Data Rest will automatically provide endpoints. It analyses the name of the class,
    and create standard endpoints like in the example: entity = Employee / endpoint = employees

    Sometimes plural form will not coincide, or maybe we want to name the path in a different way.
    We can do that with @RepositoryRestResource

    for PUT, Spring DataRest will receive the id on the URL (PathVariable), not in the body.

    Spring Data Rest only needs entities and repositories (interfaces) which extends JpaRepository

     */
}
