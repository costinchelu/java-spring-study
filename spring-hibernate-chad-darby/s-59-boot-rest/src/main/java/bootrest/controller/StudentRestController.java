package bootrest.controller;

import bootrest.exceptions.StudentErrorResponse;
import bootrest.exceptions.StudentNotFoundException;
import bootrest.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // add exception handlers
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException e) {
        // create a StudentErrorResponse and return ResponseEntity
        StudentErrorResponse errorResponse = new StudentErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

//     for a catch all
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleAllExc(Exception exc) {
        StudentErrorResponse errorResponse = new StudentErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
