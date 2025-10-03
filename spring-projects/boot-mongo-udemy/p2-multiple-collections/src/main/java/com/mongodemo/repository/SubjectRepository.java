package com.mongodemo.repository;

import com.mongodemo.entity.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectRepository extends MongoRepository<Subject, String> {

}
