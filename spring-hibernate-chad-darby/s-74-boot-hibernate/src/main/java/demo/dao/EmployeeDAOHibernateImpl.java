package demo.dao;

import demo.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

    // entity manager
    private EntityManager entityManager;


    // constructor injection
    @Autowired
    public EmployeeDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // @Transactional will be implemented in the service
    @Override
    public List<Employee> findAll() {
        // get hibernate session > query > return Result List
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Employee> query = currentSession.createQuery("select e from Employee e", Employee.class);
        return query.getResultList();
    }

    @Override
    public Employee findById(int employeeId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Employee employee = currentSession.get(Employee.class, employeeId);
        return employee;
    }

    @Override
    public void save(Employee employee) {
        Session currentSession = entityManager.unwrap(Session.class);

        // if the primary key of the employee is 0, it will insert a new employee in the DB,
        // else it will update the employee that has the existent id
        currentSession.saveOrUpdate(employee);
    }

    @Override
    public void deleteById(int employeeId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query deleteQuery = currentSession.createQuery("delete from Employee where id=:employeeId");
        deleteQuery.setParameter("employeeId", employeeId);
        deleteQuery.executeUpdate();
    }
}
