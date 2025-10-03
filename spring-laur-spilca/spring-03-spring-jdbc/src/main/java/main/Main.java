package main;


import config.ProjectConfig;
import model.Product;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import h2rest.repositories.ProductRepository;
import service.Person;

import java.util.List;


public class Main {

    public static void main(String[] args) {

        try(var c = new AnnotationConfigApplicationContext(ProjectConfig.class)) {

            ProductRepository productRepository = c.getBean(ProductRepository.class);

            Product product = new Product();
            product.setName("Coffee");
            product.setPrice(5);

            // add product
            //productRepository.addProduct(product);

            // select all
            List<Product> products = productRepository.getProducts();
            products.forEach(System.out::println);


            // stereotype part
            Person p = c.getBean(Person.class);
            p.sayHello("Bill");
        }
    }
}
