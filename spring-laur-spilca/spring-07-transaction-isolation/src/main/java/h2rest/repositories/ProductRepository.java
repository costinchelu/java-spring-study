package h2rest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addOneProduct(String name) {
        String insertOneProduct = "INSERT INTO products (name) VALUES (?)";
        jdbcTemplate.update(insertOneProduct);
    }
}
