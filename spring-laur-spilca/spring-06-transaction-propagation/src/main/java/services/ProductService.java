package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import h2rest.repositories.ProductRepository;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
    * First method will create a transaction.
    * This means that all 10 inserts will be treated as a transaction (all or none).
    * This method will call the repository method where we can have custom a propagation that will
    * modify the transaction created in the service
    *
    * */
    @Transactional
    public void addTenProducts() {
        for (int i = 0; i < 10; i++) {
            productRepository.addProduct("Product " + (i + 1));
            if (i >= 4) throw new RuntimeException("INTERRUPTED");
        }
    }

}
