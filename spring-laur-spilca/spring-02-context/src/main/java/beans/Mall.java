package beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class Mall {

    // @Autowired = we are asking Spring that we want a bean from the context
    // (the bean must exist in the context)
    // @Qualifier instructs Spring which dependency to inject
    @Autowired
    @Qualifier("gucci")
    private Store store;


    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "Mall{" +
                "store=" + store +
                '}';
    }
}
/*
We have 3 possible types of wiring
attribute,
constructor,
setter
 */