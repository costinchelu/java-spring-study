package insertsdemo.services;



import insertsdemo.controller.StudentDatabaseJPAController;
import insertsdemo.dao.entity.Student;
import insertsdemo.dao.repository.StudentJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentDatabaseJPAControllerImpl implements StudentDatabaseJPAController {

    private final StudentJPARepository studentJPARepository;

    @Override
    public Student addNewStudent(Student student) {
        return studentJPARepository.save(student);
    }

    @Override
    public List<Student> addNewStudents(Iterable<Student> students) {
        return studentJPARepository.saveAll(students);
    }

    @Override
    public Student getStudent(Integer studentRollNumber) {
        return studentJPARepository.findById(studentRollNumber).orElse(new Student());
    }

    @Override
    public List<Student> getAllStudents() {
        return studentJPARepository.findAll();
    }
}
