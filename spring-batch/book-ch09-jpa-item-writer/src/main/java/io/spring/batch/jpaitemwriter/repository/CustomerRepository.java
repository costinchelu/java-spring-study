package io.spring.batch.jpaitemwriter.repository;

import io.spring.batch.jpaitemwriter.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
