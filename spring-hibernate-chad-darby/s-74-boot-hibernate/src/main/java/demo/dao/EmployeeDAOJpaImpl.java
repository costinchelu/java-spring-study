package demo.dao;

import demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

    private EntityManager entityManager;


    @Autowired
    public EmployeeDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        Query selectAllQuery = entityManager.createQuery("select e from Employee e");
        return (List<Employee>)selectAllQuery.getResultList();
    }

    @Override
    public Employee findById(int employeeId) {
        Employee employee = entityManager.find(Employee.class, employeeId);
        return employee;
    }

    @Override
    public void save(Employee employee) {
        // insert or update:
        Employee dbEmployee = entityManager.merge(employee);
        employee.setId(dbEmployee.getId());
    }

    @Override
    public void deleteById(int employeeId) {
        Query deleteQuery = entityManager.createQuery("delete from Employee where id=:employeeId");
        deleteQuery.setParameter("employeeId", employeeId);
        deleteQuery.executeUpdate();
    }
}
