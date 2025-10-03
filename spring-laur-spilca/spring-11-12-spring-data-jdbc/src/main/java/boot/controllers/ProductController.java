package boot.controllers;

import boot.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import boot.services.ProductService;

import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/add-one")
    public void addOneProduct(@RequestBody Product p) {
        productService.addOneProduct(p);
    }

    @GetMapping("/get-by-name/{name}")
    public Product selectByName(@PathVariable String name) {
        return productService.selectProductWhereName(name);
    }

    @GetMapping("/get-by-name-2/{name}")
    public Product selectJPQL(@PathVariable String name) {
        return productService.selectJPQL(name);
    }

    @GetMapping("/get-by-name-3/{name}")
    public Product selectSQL(@PathVariable String name) {
        return productService.selectSQL(name);
    }

    @GetMapping("/select-all")
    public List<Product> selectAll() {
        return productService.getAllProducts();
    }

    @GetMapping("select-all-sorted-desc")
    public Iterable<Product> getAllSortedDescendingById() {
        return productService.selectAllSortedByIdDescending();
    }

    @GetMapping("select-all-by-page/{page}")
    public List<Product> getAllByPage(@PathVariable int page, @RequestHeader int size) {
        // actually page number starts with 0.
        // page size will be sent through a header, but it can also be sent as a path variable (.../{page}/{size})
        return productService.selectAllByPage(page - 1, size);
    }
}
