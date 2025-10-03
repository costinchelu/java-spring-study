package autowire.services;


import autowire.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Autowired is telling Spring to take from the Context a value and use the value in productRepository field
 */
@Service
public class ProductDeliveryService {

    @Autowired
    private ProductRepository productRepository;


    public void addSomeProducts() {
        productRepository.add();
        productRepository.add();
        productRepository.add();
    }
}
