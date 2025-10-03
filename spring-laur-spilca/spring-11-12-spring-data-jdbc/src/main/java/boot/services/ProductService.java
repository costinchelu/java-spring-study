package boot.services;

import boot.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import boot.repositories.ProductRepository;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addOneProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product selectProductWhereName(String name) {
        return productRepository.findProductByName(name);
    }

    public Product selectJPQL(String name) {
        return productRepository.jpqlQuery(name);
    }

    public Product selectSQL(String name) {
        return productRepository.sqlQuery(name);
    }

    // using Sort
    public Iterable<Product> selectAllSortedByIdDescending() {
        return productRepository.findAll(Sort.by("id").descending());
    }

    // using Page
    public List<Product> selectAllByPage(int page, int size) {
        // we can also use sorting:
        Sort nameAscending = Sort.by("name").ascending();
        return productRepository.findAll(PageRequest.of(page, size, nameAscending)).getContent();
    }
}
