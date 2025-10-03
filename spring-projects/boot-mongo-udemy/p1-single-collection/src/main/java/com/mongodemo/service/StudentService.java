package com.mongodemo.service;

import com.mongodemo.entity.Student;
import com.mongodemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;


    public Student createStudent (Student student) {
         return studentRepository.save(student);
    }

    public Student getStudentById (String id) {
        return studentRepository.findById(id).get();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent (Student student) {
        return studentRepository.save(student);
    }

    public String deleteStudent (String id) {
        studentRepository.deleteById(id);
        return "Student " + id + " has been deleted";
    }

    public List<Student> getStudentsByName (String name) {
        return studentRepository.findByName(name);
    }

    public List<Student> getStudentByNameAndEmail(String name, String email) {
        return studentRepository.findByNameAndAndEmail(name, email);
    }

    public List<Student> getStudentByNameOrEmail(String name, String email) {
        return studentRepository.findByNameOrEmail(name, email);
    }

    public List<Student> getAllWithPagination(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return studentRepository.findAll(pageable).getContent();
    }

    public List<Student> getAllWithSorting() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name", "email");
        return studentRepository.findAll(sort);
    }

    public List<Student> getStudentsByDepartmentName(String departmentName) {
        return studentRepository.findByDepartmentDepartmentName(departmentName);
    }

    public List<Student> getStudentsBySubjectName(String subjName) {
        return studentRepository.findBySubjectsSubjectName(subjName);
    }

    public List<Student> getStudentsByEmailLike(String emailString) {
        return studentRepository.findByEmailLike(emailString);
    }

    public List<Student> getStudentsByNameStartingWith(String nameString) {
        return studentRepository.findByNameStartsWith(nameString);
    }

}
