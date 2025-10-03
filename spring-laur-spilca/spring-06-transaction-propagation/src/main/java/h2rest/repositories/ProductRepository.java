package h2rest.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductRepository {

    /**
     * REQUIRED = default
     * REQUIRES_NEW = always creates a new transaction. Even if first transaction is created, every time we have a REQ_NEW propagation, the annotated method will create another transaction
     * MANDATORY = need to be called by a transaction, or else an exception will be thrown
     * NEVER = opposite of mandatory (in case we receive a transaction the method will not run)
     * SUPPORTS = called with a transaction if that exists, but also called if the transaction id not created (only that in the second case it will not run as a transaction)
     * NOT_SUPPORTED = does not create a transaction when it is called (even if call this from a transaction this will run on a non transactional context. Example, when we have a select)
     * NESTED = rarely used. Nested transactions within other transaction.
     */

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addProduct(String name) {
        String sql = "INSERT INTO products (name) VALUES (?)";
        jdbcTemplate.update(sql, name);
    }

}
