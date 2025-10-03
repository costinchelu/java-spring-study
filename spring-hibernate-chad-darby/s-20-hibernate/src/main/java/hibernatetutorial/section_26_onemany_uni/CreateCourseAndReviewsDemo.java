package hibernatetutorial.section_26_onemany_uni;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import hibernatetutorial.section_26_onemany_uni.entity.Course;
import hibernatetutorial.section_26_onemany_uni.entity.Instructor;
import hibernatetutorial.section_26_onemany_uni.entity.InstructorDetail;
import hibernatetutorial.section_26_onemany_uni.entity.Review;

import java.util.ArrayList;
import java.util.List;


public class CreateCourseAndReviewsDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.review.xml";

    public static void main(String[] args) {

        List<Course> chadCourses = new ArrayList<>();
        chadCourses.add(new Course("Course 1"));
        chadCourses.add(new Course("Course 2"));
        chadCourses.add(new Course("Course 3"));

        // we need all linked tables from this schema to be added in the session factory
        SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Review.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            // we associate courses with the instructor, and then save every course in the db
            for(Course c : chadCourses) {
                session.save(c);    // this will make a persistent course and every operation we are making is real time
                c.addReview(new Review("Excellent!!!"));
                c.addReview(new Review("Great course! :)"));
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            session.close();
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}
