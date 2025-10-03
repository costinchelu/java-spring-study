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

    @GetMapping("students-by-email-like")
    public List<Student> studentsByEmailLike(@RequestParam String emailString) {
        return studentService.getStudentsByEmailLike(emailString);
    }

    @GetMapping("students-by-name-starting-with")
    public List<Student> studentsByNameStartingWith(@RequestParam String nameString) {
        return studentService.getStudentsByNameStartingWith(nameString);
    }

    // getting the student by the department id when we only have a reference to department in the student document
    @GetMapping("students-by-department-id")
    public List<Student> studentsByDepartmentId(@RequestParam String deptId) {
        return studentService.getStudentsByDepartmentId(deptId);
    }

    // native query
    // example:
    // http://localhost:8080/api/student/students-by-name-and-email-string-native-query?name=Peter&strMail=st
    @GetMapping("students-by-name-and-email-string-native-query")
    public List<Student> getStudentsByNameNative(@RequestParam String name, @RequestParam String strMail) {
        return studentService.getStudentsByNameNative(name, strMail);
    }
}
