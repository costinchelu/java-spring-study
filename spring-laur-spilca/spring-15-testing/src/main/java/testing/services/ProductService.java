package testing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testing.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public List<String> getProductNamesWithEvenNoOfChar() {
        List<String> names = productRepository.getProductNames();
        List<String> result = new ArrayList<>();
        for (String n: names) {
            if (n.length() % 2 == 0) {
                result.add(n);
//                productRepository.addProduct(n);
            }
        }
        return result;
    }

    public List<String> getProductNamesWithEvenNoOfCharRefactored() {
        List<String> names = productRepository.getProductNames();
        return names.stream()
                .filter(n -> n.length() % 2 == 0)
                .collect(Collectors.toList());
    }
}
