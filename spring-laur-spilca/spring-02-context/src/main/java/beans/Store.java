package beans;

import org.springframework.stereotype.Component;


@Component
public class Store {

    public String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                '}';
    }
}
