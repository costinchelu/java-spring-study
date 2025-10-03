package springjdbc.repository;

import springjdbc.entity.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@AllArgsConstructor
@Transactional
public class EmployeeRepository {

    EntityManager em;

    public void insert(Employee employee) {
        em.persist(employee);
    }

    public List<Employee> retrieveAllEmployees() {
        return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }
}
