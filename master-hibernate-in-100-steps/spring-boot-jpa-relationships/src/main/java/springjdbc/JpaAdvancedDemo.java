package springjdbc;

import lombok.RequiredArgsConstructor;
import springjdbc.entity.Address;
import springjdbc.entity.Course;
import springjdbc.entity.Student;
import springjdbc.repository.CourseSpringDataRepository;
import springjdbc.repository.StudentSpringDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * SELECT * FROM student s, passport p, review r, course_details c, student_course sc WHERE s.passport_id = p.id AND r.student_id = s.id AND (s.id = sc.student_id AND c.id = sc.course_id);
 */
@Slf4j
@EntityScan("springjdbc.entity")
@SpringBootApplication
@RequiredArgsConstructor
public class JpaAdvancedDemo implements CommandLineRunner {

    private final CourseSpringDataRepository courseRepo;

    private final StudentSpringDataRepository studentRepo;

    private final EntityManager em;

    private final Course noCourseFound = new Course(0L, "No course found", null, null, null, null, false);

    private final Student noStudentFound = new Student(0L, "No student found", null, null, null, null);

    public static void main(String[] args) {
        SpringApplication.run(JpaAdvancedDemo.class, args);
    }

    @Override
    public void run(String... args) {
        springDataJpaDemo();
    }

    private void springDataJpaDemo() {

        // READ
        Optional<Course> courseById = courseRepo.findById(10001L);
        log.info("Course with ID 10001: {}", courseById.orElse(noCourseFound));
        log.info("Course with ID 20001: {}", courseRepo.findById(20001L).orElse(noCourseFound));

        // CREATE - UPDATE
        // to create or update, we can use save()
        // any change to the newly persisted Course entity will not be automatically updated
        // after changing an entity, we need to explicitly save that entity to see the updated change in DB
        Course newCourse = Course.builder().name("Javascript 101").build();
        courseRepo.save(newCourse);

        newCourse.setName("Javascript 102");

        Student student20002 = studentRepo.findById(20002L).orElse(noStudentFound);
        newCourse.addStudent(student20002);
        student20002.addCourse(newCourse);

        courseRepo.save(newCourse);
        studentRepo.save(student20002);

        log.info("Number of courses in DB: {}", courseRepo.count());

        // SORTING
        log.info("All courses in the descending order by name: {}",
                courseRepo.findAll(Sort.by(Sort.Direction.DESC, "name")));

        // PAGINATION
        List<Course> firstPageOfCourses =
                courseRepo.findAll(PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "id")))
                        .get()
                        .collect(Collectors.toList());
        log.info("First page: {}", firstPageOfCourses);

        // CUSTOM METHODS
        log.info("Find by name ending with: {}", courseRepo.findByNameEndingWith("9").orElse(List.of(noCourseFound)));
        log.info("Find by name containing: {}", courseRepo.findByNameContaining("4").orElse(List.of(noCourseFound)));

        // native queries
        log.info("Using native query: {}", courseRepo.nativeQueryExample(10005L).orElse(List.of(noCourseFound)));

        // using named queries
        log.info("Named query example: {}", courseRepo.namedQueryExample().orElse(List.of(noCourseFound)));

        // using JPQL
        log.info("JPQL example: {}", courseRepo.jpqlExample(2).orElse(List.of(noCourseFound)));

        // SOFT DELETE
        // using @SQLDelete instead of deleting a course we will mark it as deleted using the boolean property
        courseRepo.deleteById(10010L);
        log.info("After deleting COURSE 10: {}", courseRepo.findAll());

        // inserting persisting embeddable object details into DB:
        student20002.setAddress(new Address("Address details line 1", "Address details line 2", "Antananarivo"));
        studentRepo.save(student20002);

        // N+1 PROBLEM
        // for each course we will have another JDBC query to get the students enrolled
        // this is a performance issue. What will it happen if we have 50000 courses (we will have 50001 queries fired)
        creatingNPlusOneProblem();
        // one solution could be Entity graph
        usingAnEntityGraphToSolveNPlusOne();
        // another would be a joined fetch
        usingAJoinFetchToSolveNPlusOne();
    }

    private void creatingNPlusOneProblem() {
        log.info("N + 1 problem");
        List<Course> courses = courseRepo.findAll();
        courses.forEach(course -> log.info("Course: {}, students: {}", course, course.getStudents()));
    }

    @Transactional
    public void usingAnEntityGraphToSolveNPlusOne() {
        log.info("Solving N + 1 problem with entity graph");

        // will load courses and students as well (like in an eager fetch)
        EntityGraph<Course> entityGraph = em.createEntityGraph(Course.class);
        entityGraph.addSubgraph("students");

        List<Course> courses = em
                .createNamedQuery("get_all_courses", Course.class)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();

        courses.forEach(course -> log.info("Course: {}, students: {}", course, course.getStudents()));
    }

    private void usingAJoinFetchToSolveNPlusOne() {
        log.info("Solving N + 1 problem with join fetch");

        List<Course> courses = em
                .createNamedQuery("get_all_courses_join_fetch_students", Course.class)
                .getResultList();

        courses.forEach(course -> log.info("Course: {}, students: {}", course, course.getStudents()));
    }
}

