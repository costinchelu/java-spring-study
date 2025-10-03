package main;

import config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.ProductService;


public class Main {

    public static void main(String[] args) {

        try (var c = new AnnotationConfigApplicationContext(ProjectConfig.class)) {

            ProductService productService = c.getBean(ProductService.class);
            productService.addOneProduct("Butter");
        }
    }
}
