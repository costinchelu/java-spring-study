package autowire.repositories;


import org.springframework.stereotype.Repository;


@Repository
public class ProductRepository {

    public void add() {
        System.out.println("A new product was added to DB");
    }
}
