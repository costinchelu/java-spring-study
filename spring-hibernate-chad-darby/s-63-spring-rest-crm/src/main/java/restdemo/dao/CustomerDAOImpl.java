package restdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import restdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

			
	@Override
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		// create a query  ... sort by last name
		Query<Customer> selectAllSorted =
				currentSession.createQuery("from Customer order by lastName", Customer.class);
		
		// execute query and get result list
		List<Customer> customers = selectAllSorted.getResultList();
				
		// return the results		
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save/update the customer
		currentSession.saveOrUpdate(customer);
		
	}

	@Override
	public Customer getCustomer(int customerId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/read from database using the primary key
		return currentSession.get(Customer.class, customerId);
	}

	@Override
	public void deleteCustomer(int customerId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete object with primary key
		Query delete = currentSession.createQuery("delete from Customer where id=:customerId");
		delete.setParameter("customerId", customerId);
		
		delete.executeUpdate();
	}
}











