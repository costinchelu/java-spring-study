package hibernatetutorial.section_24_onemany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import hibernatetutorial.section_24_onemany.entity.Course;
import hibernatetutorial.section_24_onemany.entity.Instructor;
import hibernatetutorial.section_24_onemany.entity.InstructorDetail;

import java.util.ArrayList;
import java.util.List;


public class CreateCoursesDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.course.xml";
    private static final int CHAD_ID = 1;

    public static void main(String[] args) {

        List<Course> chadCourses = new ArrayList<>();
        chadCourses.add(new Course("Course 1"));
        chadCourses.add(new Course("Course 2"));
        chadCourses.add(new Course("Course 3"));

        SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            // we work on a persisted instructor object (all mods will be automatically written in the DB)
            Instructor instructorChad = session.get(Instructor.class, CHAD_ID);

            // we associate courses with the instructor, and then save every course in the db
            for(Course c : chadCourses) {
                instructorChad.addCourse(c);
                session.save(c);
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            session.close();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
