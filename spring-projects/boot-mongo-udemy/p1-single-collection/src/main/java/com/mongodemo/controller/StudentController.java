package com.mongodemo.controller;

import com.mongodemo.entity.Student;
import com.mongodemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;


    @PostMapping("/create")
    public Student createStudent (@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/get-by-id/{id}")
    public Student getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/students-by-name/{name}")
    public List<Student> studentsByName(@PathVariable String name) {
        return studentService.getStudentsByName(name);
    }

    @GetMapping("/students-by-name-and-email")
    public List<Student> studentsByNameAndEmail (@RequestParam String name,@RequestParam String email) {
        return studentService.getStudentByNameAndEmail(name, email);
        // http://localhost:8080/api/student/students-by-name-and-email?name=Peter&email=peter@gmail.com
    }

    @GetMapping("/students-by-name-or-email")
    public List<Student> studentsByNameOrEmail (@RequestParam String name,@RequestParam String email) {
        return studentService.getStudentByNameOrEmail(name, email);
    }

    // using pagination:
    @GetMapping("/all-with-pagination")
    public List<Student> getAllWithPagination(@RequestParam int pageNo, @RequestParam int pageSize) {
        return studentService.getAllWithPagination(pageNo, pageSize);
    }
    @GetMapping("/all-with-sorting")
    public List<Student> getAllWithSorting() {
        return studentService.getAllWithSorting();
    }

    // get with the form: http://localhost:8080/api/student/students-by-department-name/Computer%20Science
    @GetMapping("/students-by-department-name/{departmentName}")
    public List<Student> studentsByDepartmentNamePath (@PathVariable String departmentName) {
        return studentService.getStudentsByDepartmentName(departmentName);
    }

    // get with the form: http://localhost:8080/api/student/students-by-department-name-param?dn=Computer%20Science
    @GetMapping("/students-by-department-name-param")
    public List<Student> studentsByDepartmentNameParam (@RequestParam String dn) {
        return studentService.getStudentsByDepartmentName(dn);
    }

    // get with the form: http://localhost:8080/api/student/students-by-department-name-body
    // put in the body: Computer Science
    @GetMapping("students-by-department-name-body")
    public List<Student> studentsByDepartmentNameBody (@RequestBody String dn) {
        return studentService.getStudentsByDepartmentName(dn);
    }

    // get with the form: http://localhost:8080/api/student/students-by-subject-name?subjName=MongoDB
    @GetMapping("students-by-subject-name")
    public List<Student> studentsBySubjectName(@RequestParam String subjName) {
        return studentService.getStudentsBySubjectName(subjName);
    }

    @GetMapping("students-by-email-like")
    public List<Student> studentsByEmailLike(@RequestParam String emailString) {
        return studentService.getStudentsByEmailLike(emailString);
    }

    @GetMapping("students-by-name-starting-with")
    public List<Student> studentsByNameStartingWith(@RequestParam String nameString) {
        return studentService.getStudentsByNameStartingWith(nameString);
    }
}
