package springjdbc.repository;

import springjdbc.entity.Course;
import springjdbc.entity.Passport;
import springjdbc.entity.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;


@AllArgsConstructor
@Transactional
@Repository
public class StudentRepository {

    private EntityManager em;

    public Student findById(Long id) {
        return em.find(Student.class, id);
    }

    public void deleteById(Long id) {
        em.remove(findById(id));
    }

    public Student saveStudentWithPassword(Student student, Passport passport) {
        // we need to persist password first or else we get a TransientPropertyValueException
        em.persist(passport);
        student.setPassport(passport);
        em.persist(student);
        return student;
    }

    public Passport findPassportById(Long id) {
        return em.find(Passport.class, id);
    }

    public List<Student> getAllStudents() {
        return em.createNamedQuery("get_all_students", Student.class).getResultList();
    }

    public List<Student> jpqlQuerySimpleLike() {
        return em.createQuery("SELECT s FROM Student s WHERE s.name LIKE 'J%'", Student.class)
                .getResultList();
    }

    public List<Student> jpqlQueryStudentsWithPassportNumberLike() {
        return em.createQuery("SELECT s FROM Student s WHERE s.passport.number LIKE '%12%'", Student.class)
                .getResultList();
    }

    public List<Student> nativeQuery() {
        Query query = em.createNativeQuery("SELECT * FROM student WHERE name LIKE ?", Student.class);
        query.setParameter(1, "J%");
        return query.getResultList();
    }

    public List<Student> nativeQueryWithNamedParameter() {
        Query query = em.createNativeQuery("SELECT * FROM student WHERE name LIKE :like1", Student.class);
        query.setParameter("like1", "J%");
        return query.getResultList();
    }

    public int nativeQueryToUpdate() {
        Query query = em.createNativeQuery("UPDATE student SET name = 'Johny' WHERE id = 20001");
        return query.executeUpdate();
    }

    public Student findStudentByPassportNumber(String passportNUmber) {
        Query query = em.createNativeQuery(
                "SELECT * FROM student WHERE passport_id IN (SELECT id FROM passport WHERE number = ?)",
                Student.class);
        query.setParameter(1, passportNUmber);
        List<Student> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public void addStudentAndCourse(Student student, Course course) {
        student.addCourse(course);
        course.addStudent(student);
        em.persist(student);
        em.persist(course);
    }
}
