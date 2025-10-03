package restdemo.controller;

import restdemo.entity.Student;
import restdemo.exceptions.StudentNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> students;

    // define @PostConstruct to load the student data only once
    @PostConstruct
    public void loadData() {
        students = new ArrayList<>();
        students.add(new Student("John", "Doe"));
        students.add(new Student("Clark", "Kent"));
        students.add(new Student("Bruce", "Wayne"));
    }

    // get the list of students
    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {
        // studentId = index of the object in the list
        if (students.size() <= studentId || studentId < 0) {
            throw new StudentNotFoundException("Student id is not found - " + studentId);
        }

        return students.get(studentId);
    }
}
