package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import h2rest.repositories.ProductRepository;

import java.sql.SQLException;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * will wrap the method in a transaction. In case we have an exception during this method, then
     * all data will be rolled back in the database
     *
     * rolls back RuntimeException but not checked exception
     */
    @Transactional (rollbackFor = {RuntimeException.class, SQLException.class})
    public void addOneProduct(String productName) {
        productRepository.addProduct(productName);
        // case we have a RuntimeException or an SQLException, transaction will be rolled back
        //if(true) throw  new SQLException();

        System.out.println(productName + " was added successfully!");
    }
}
