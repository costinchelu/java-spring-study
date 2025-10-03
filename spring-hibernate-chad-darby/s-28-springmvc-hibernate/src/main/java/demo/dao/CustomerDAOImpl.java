package demo.dao;

import demo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


// @Repository is always applied to DAO Implementations (component scan & Spring handles exception translations)
@Repository
public class CustomerDAOImpl implements CustomerDAO {


    // autowired from the servlet.xml bean configuration file
    @Autowired
    private SessionFactory sessionFactory;


    // the service will manage the transaction and make calls to DAOs
    @Override
    public List<Customer> getCustomers() {

        // get the current Hibernate session (was autowired = injected)
        Session currentSession = sessionFactory.getCurrentSession();

        // create a query and execute it (SELECT * FROM customer ORDER BY last_name) to get result list
        Query<Customer> query = currentSession.createQuery("FROM Customer ORDER BY lastName", Customer.class);

        //return list
        return query.getResultList();
    }

    // add or update customer to DB (by using customer id )
    @Override
    public void saveCustomer(Customer customer) {

        Session currentSession = sessionFactory.getCurrentSession();

        // save customer to DB (insert into...) or update customer (update customer set ...)
        currentSession.saveOrUpdate(customer);
    }

    // get a single customer (for updating form fields)
    @Override
    public Customer getCustomer(int customerId) {

        Session session = sessionFactory.getCurrentSession();

        return session.get(Customer.class, customerId);
    }

    // delete a customer
    @Override
    public void deleteCustomer(int customerId) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("DELETE FROM Customer WHERE id=:idToDelete");
        query.setParameter("idToDelete", customerId);
        query.executeUpdate();
    }

    @Override
    public List<Customer> searchCustomers(String searchName) {

        Session session = sessionFactory.getCurrentSession();

        Query<Customer> query = null;

        // only search by name if searchName is not empty (else get all of them)
        if (searchName != null && searchName.trim().length() > 0) {

            // better to search in first_name and last_name as case insensitive
            query = session.createQuery
                    ("FROM Customer WHERE LOWER(firstName) LIKE :name OR LOWER(lastName) LIKE :name",
                            Customer.class);

            // using % wildcard before and after name will search a substring regardless of position in the name
            query.setParameter("name", "%" + searchName.toLowerCase() + "%");

        } else {
            // in this case searchName is empty so we are getting all customers
            query = session.createQuery("from Customer", Customer.class);
        }

        return query.getResultList();
    }
}
