package springjdbc;

import springjdbc.entity.Course;
import springjdbc.entity.Passport;
import springjdbc.entity.Review;
import springjdbc.entity.Student;
import springjdbc.repository.CourseRepository;
import springjdbc.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

/**
 * SELECT * FROM student s, passport p, review r, course_details c, student_course sc WHERE s.passport_id = p.id AND r.student_id = s.id AND (s.id = sc.student_id AND c.id = sc.course_id);
 */
@Slf4j
@AllArgsConstructor
@EntityScan("springjdbc.entity")
@SpringBootApplication
public class JpaAdvancedDemo implements CommandLineRunner {

    private CourseRepository courseRepository;

    private StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaAdvancedDemo.class, args);
    }

    @Override
    public void run(String... args) {
        courseMethods();
        studentMethods();
        usingJpql();
        simpleJoin();
        usingCriteria();
    }

    private void courseMethods() {
        Course course = courseRepository.findById(10001L);
        log.info("Find course by id 10001: {}", course.getName());

//        courseRepository.deleteById(10001L);
//        log.info("Course 1 deleted");

        log.info("Inserting a new course: {}", courseRepository.save(Course.builder().name("COURSE 4").build()));

        Course course4 = courseRepository.findById(1L);
        log.info("Course 4: {}", course4);
        course4.setName("COURSE 4 (updated)");

        log.info("Updating course: {}", courseRepository.save(course4));

        log.info("All courses:");
        courseRepository.namedQueryGetAllCourses().forEach(c -> log.info(c.toString()));

        log.info("Some courses (JPQL):");
        courseRepository.jpqlQuery().forEach(c -> log.info(c.toString()));

        log.info("Some courses (SQL):");
        courseRepository.nativeQuery().forEach(c -> log.info(c.toString()));

        log.info("Courses updated = {}", courseRepository.nativeQueryToUpdate());

        log.info("Adding a review:");
        courseRepository.addReview(course.getId(), 20002L, "Really useful", "4");

        log.info("Adding multiple reviews:");
        List<Review> reviews = List.of(
                Review.builder().rating("5").description("Great stuff").build(),
                Review.builder().rating("4").description("Very good").build());
        courseRepository.addReviews(10002L, 20002L, reviews);
    }

    private void studentMethods() {
        Student student = studentRepository.findById(20001L);
        log.info("Find student by id 20001: {}", student.getName());

        log.info("Get student's passport number: {}", student.getPassport().getNumber());

        log.info("Inserting a new student: {}",
                studentRepository.saveStudentWithPassword(
                        Student.builder()
                                .name("Paul")
                                .build(),
                        Passport.builder()
                                .number("S4474")
                                .build()));

        log.info("Student associated with passport number J4585 is {}",
                studentRepository.findStudentByPassportNumber("J4585").getName());

        Passport passport = studentRepository.findPassportById(40002L);
        log.info("Student associated with passport id 40002 is {}", passport.getStudent().getName());

        log.info("Courses associated with student id 10001: {}", studentRepository.findById(20001L).getCourses());

        log.info("Adding a student and a course:");
        Student student1 = Student.builder().name("Kevin").build();
        Course course1 = Course.builder().name("Python").build();
        studentRepository.addStudentAndCourse(student1, course1);


//        log.info("Some students (SQL):");
//        studentRepository.nativeQuery().forEach(s -> log.info(s.toString()));

//        log.info("All students:");
//        studentRepository.getAllStudents().forEach(s -> log.info(s.toString()));
    }

    private void usingJpql() {
        log.info("Courses that contains 1 or 3 in the course name: {}", courseRepository.jpqlQuery());
        log.info("All courses without any students: {}", courseRepository.jpqlQueryAllCoursesWithoutStudents());
        log.info("All courses with at least 2 students enrolled: {}", courseRepository.jpqlQueryCoursesWithAtLeast2Students());
        log.info("Order courses by number of enrolled students: {}", courseRepository.jpqlQueryCoursesOrderedByNoOfStudents());
        log.info("Students with name like: {}", studentRepository.jpqlQuerySimpleLike());
        log.info("Students with passport number like: {}", studentRepository.jpqlQueryStudentsWithPassportNumberLike());
    }

    /**
     * Result will be for each course, an associated student. Results for this type of joins consists of a list of arrays
     * (2 objects = 2 arrays per line)
     * Only courses that have an associated student will be gathered
     *
     * For a LEFT JOIN, all the courses will be gathered (even those that have a null student_id)
     *
     * CROSS-JOIN is not an actual join, but more of a gather-up of all the combinations between course and student.
     * The number of lines will be = no of courses x no of student [SELECT c, s FROM Course c, Student s]
     */
    private void simpleJoin() {
        List<Object[]> joinResult = courseRepository.simpleJoin();
        log.info("Simple join result list size = {}", joinResult.size());
        joinResult.forEach(o -> log.info("{} {}", o[0], o[1]));
    }

    private void usingCriteria() {
        log.info("SELECT * FROM course - using Criteria: {}", courseRepository.criteriaSelectAllCourses());
        log.info("SELECT * FROM course WHERE name LIKE '%1' - using Criteria: {}", courseRepository.criteriaCourseNameContains1());
        log.info("SELECT c FROM Course c WHERE c.students IS EMPTY - using criteria: {}", courseRepository.criteriaAllCoursesWithoutStudents());
        log.info("{}", courseRepository.criteriaLeftJoinCourseWithStudents());
    }
}

