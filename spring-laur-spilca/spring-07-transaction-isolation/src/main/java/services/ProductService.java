package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import h2rest.repositories.ProductRepository;

@Service
public class ProductService {

    /**
     * DEFAULT = taken from the under layer (database)
     * READ_UNCOMMITTED = all 3 problems can occur
     * READ_COMMITTED =  last 2 problems can occur
     * REPEATABLE_READ = last problem can occur
     * SERIALIZABLE = no problem can occur
     *
     * PROBLEMS:
     * - dirty reads
     * - repeatable reads
     * - phantom reads
     */


    @Autowired
    private ProductRepository productRepository;


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addTenProductsAtOnce() {
        for (int i = 0; i < 10; i++) {
            productRepository.addOneProduct("Product " + (i + 1));
        }
    }
}
