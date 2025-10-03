package com.mongodemo.repository;

import com.mongodemo.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    List<Student> findByName(String name);

    List<Student> findByNameAndAndEmail(String name, String email);

    List<Student> findByNameOrEmail(String name, String email);

    List<Student> findByDepartmentDepartmentName(String departmentName);

    List<Student> findBySubjectsSubjectName(String subjName);

    List<Student> findByEmailLike(String emailString);

    List<Student> findByNameStartsWith(String nameString);
}
