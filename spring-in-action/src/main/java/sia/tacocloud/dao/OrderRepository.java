package sia.tacocloud.dao;

import sia.tacocloud.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
