package com.mongodemo.repository;

import com.mongodemo.entity.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {

}
