package insertdemo.services;


import insertdemo.controller.StudentDatabaseJDBCController;
import insertdemo.dao.entity.Student;
import insertdemo.dao.repository.StudentJDBCRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StudentDatabaseJDBCControllerImpl implements StudentDatabaseJDBCController {

    private final StudentJDBCRepository studentJDBCRepository;

    @Override
    public Student addNewStudent(Student student) {
        return studentJDBCRepository.save(student);
    }

    @Override
    public Iterable<Student> addNewStudents(Iterable<Student> students) {
        return studentJDBCRepository.saveAll(students);
    }

    @Override
    public Student getStudent(Integer studentRollNumber) {
        return studentJDBCRepository.findById(studentRollNumber).orElse(new Student());
    }

    @Override
    public Iterable<Student> getAllStudents() {
        return studentJDBCRepository.findAll();
    }
}
