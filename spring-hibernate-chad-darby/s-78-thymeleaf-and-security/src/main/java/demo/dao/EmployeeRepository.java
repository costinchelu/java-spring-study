package demo.dao;

import demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // method to sort by lastName
    List<Employee> findAllByOrderByLastNameAsc();

    // search by name
    List<Employee> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String name, String lName);
}
