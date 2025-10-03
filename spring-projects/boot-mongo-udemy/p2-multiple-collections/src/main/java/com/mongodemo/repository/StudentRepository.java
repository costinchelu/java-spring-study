package com.mongodemo.repository;

import com.mongodemo.entity.Student;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    List<Student> findByName(String name);

    List<Student> findByNameAndAndEmail(String name, String email);

    List<Student> findByNameOrEmail(String name, String email);

    List<Student> findByEmailLike(String emailString);

    List<Student> findByNameStartsWith(String nameString);

    List<Student> findByDepartmentId(String detId);

    @Query("{$or: [{\"name\": \"?0\"},{\"mail\": /^?1/}]}")
    List<Student> findByNameAndStringMailNative(String name, String strMail);

}
