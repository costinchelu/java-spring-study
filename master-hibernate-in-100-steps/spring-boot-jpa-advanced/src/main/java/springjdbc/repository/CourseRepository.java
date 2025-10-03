package springjdbc.repository;

import springjdbc.entity.Course;
import springjdbc.entity.Review;
import springjdbc.entity.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
@AllArgsConstructor
public class CourseRepository {

    // whenever we are managing an entity with the E.M. all changes to that entity is automatically persisted
    // we can remove the association between the entity and E.M. by using detach()
    private EntityManager em;

    private StudentRepository studentRepository;

    public Course findById(Long id) {
        return em.find(Course.class, id);
    }

    // change in entity data is done within a transaction (@Transactional is used)
    public void deleteById(Long id) {
        em.remove(findById(id));
    }

    public Course save(Course course) {
        if (course.getId() == null) {
            em.persist(course);
        } else {
            em.merge(course);
        }
        return course;
    }

    public List<Course> namedQueryGetAllCourses() {
        return em.createNamedQuery("get_all_courses", Course.class).getResultList();
    }

    public List<Course> jpqlQuery() {
        return em.createQuery("SELECT c FROM Course c WHERE c.name LIKE '% 1' OR c.name LIKE '% 3'", Course.class)
                .getResultList();
    }

    public List<Course> jpqlQueryAllCoursesWithoutStudents() {
        TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c WHERE c.students IS EMPTY", Course.class);
        return query.getResultList();
    }

    public List<Course> jpqlQueryCoursesWithAtLeast2Students() {
        TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c WHERE SIZE(c.students) >= 2", Course.class);
        return query.getResultList();
    }

    public List<Course> jpqlQueryCoursesOrderedByNoOfStudents() {
        TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c ORDER BY SIZE(c.students) DESC", Course.class);
        return query.getResultList();
    }

    public List<Course> nativeQuery() {
        Query result = em.createNativeQuery(
                "SELECT * FROM course_details WHERE name LIKE ? OR name LIKE ?",
                Course.class);
        result.setParameter(1, "% 1");
        result.setParameter(2, "% 3");
        return result.getResultList();
    }

    public List<Course> nativeQueryWithNamedParameter() {
        Query result = em.createNativeQuery(
                "SELECT * FROM course_details WHERE name LIKE :like1 OR name LIKE :like2",
                Course.class);
        result.setParameter("like1", "% 1");
        result.setParameter("like2", "% 3");
        return result.getResultList();
    }

    public int nativeQueryToUpdate() {
        Query query = em.createNativeQuery("UPDATE course_details SET last_updated = CURRENT_TIMESTAMP");
        return query.executeUpdate();
    }

    public Review saveReview(Review review) {
        if (review.getId() == null) {
            em.persist(review);
        } else {
            em.merge(review);
        }
        return review;
    }

    public void addReview(Long courseId, Long studentId, String reviewBody, String courseRating) {
        // 1. get course and student
        Course course = findById(courseId);
        Student student = studentRepository.findById(studentId);
        // 2. create a review and associate it to a course
        Review newReview = Review.builder()
                        .description(reviewBody)
                        .rating(courseRating)
                        .course(course)
                        .student(student)
                        .build();
        // 3. add the newly create review to the course's review list
        course.addReview(newReview);
        student.addReview(newReview);
        // 4. save the new review to DB (this need to be persisted but the course is already managed by entity manager)
        saveReview(newReview);
    }

    /**
     * 1. get relations
     * 2. add each review to relations
     * 3. set each relation to each review
     * 4. persist each review (relations are already persisted (managed by the entity manager))
     */
    public void addReviews(Long courseId, Long studentId, List<Review> reviews) {
        Course course = findById(courseId);
        Student student = studentRepository.findById(studentId);
        reviews.forEach(r -> {
            course.addReview(r);
            student.addReview(r);
            r.setCourse(course);
            r.setStudent(student);
            em.persist(r);
        });
    }

    public void addHardcodedReviews() {
        Course course = findById(10001L);

        // create the reviews
        Review review1 = Review.builder().rating("5").description("Great stuff").build();
        Review review2 = Review.builder().rating("4").description("Very good").build();

        // get previous reviews for that course and add new reviews to the list (add review to course)
        course.addReview(review1);
        course.addReview(review2);

        // set course to review
        review1.setCourse(course);
        review2.setCourse(course);
    }

    public List<Object[]> simpleJoin() {
        String simpleJoin = "SELECT c, s FROM Course c JOIN c.students s";
        Query query = em.createQuery(simpleJoin);
        return query.getResultList();
    }

    public List<Course> criteriaSelectAllCourses() {
        // result would consist on a JPQL like: "SELECT c FROM Course c"
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);
        Root<Course> root = cq.from(Course.class);
        TypedQuery<Course> query = em.createQuery(cq.select(root));
        return query.getResultList();
    }

    public List<Course> criteriaCourseNameContains1() {
        // result would consist on a native SQL like: "SELECT * FROM course WHERE name LIKE '%1'"
        // 1. Use Criteria Builder to create a Criteria Query returning the expected result object
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);
        // 2. Define roots for tables which are involved in the query
        Root<Course> root = cq.from(Course.class);
        // 3. Define predicates etc using Criteria Builder
        Predicate likePredicate = cb.like(root.get("name"), "%1");
        // 4. Add Predicates etc to the Criteria Query
        cq.where(likePredicate);
        // 5. Build the TypedQuery using the EntityManager and the Criteria Query (select from Course)
        TypedQuery<Course> query = em.createQuery(cq.select(root));
        return query.getResultList();
    }

    public List<Course> criteriaAllCoursesWithoutStudents() {
        // result would consist on a JPQL like: "SELECT c FROM Course c WHERE c.students IS EMPTY"
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);
        Root<Course> root = cq.from(Course.class);
        Predicate noStudents = cb.isEmpty(root.get("students"));
        cq.where(noStudents);
        TypedQuery<Course> query = em.createQuery(cq.select(root));
        return query.getResultList();
    }

    public List<Course> criteriaLeftJoinCourseWithStudents() {
        // result would consist on a JPQL like: "SELECT c FROM Course c JOIN c.students s"
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);
        Root<Course> courseRoot = cq.from(Course.class);
        courseRoot.join("students", JoinType.LEFT);
        TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
        return query.getResultList();
    }
}
