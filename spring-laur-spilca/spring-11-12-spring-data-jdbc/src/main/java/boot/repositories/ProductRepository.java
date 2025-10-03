package boot.repositories;

import boot.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductByName(String name);

    // JPA Query
    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Product jpqlQuery(String name);

    // native Query
    @Query(
            value = "SELECT * FROM products WHERE name = :name",
            nativeQuery = true
    )
    Product sqlQuery(String name);
}
