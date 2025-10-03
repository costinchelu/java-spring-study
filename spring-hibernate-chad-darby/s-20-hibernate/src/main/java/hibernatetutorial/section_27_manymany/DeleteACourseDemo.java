package hibernatetutorial.section_27_manymany;

import hibernatetutorial.section_27_manymany.entity.Course;
import hibernatetutorial.section_27_manymany.entity.Instructor;
import hibernatetutorial.section_27_manymany.entity.InstructorDetail;
import hibernatetutorial.section_27_manymany.entity.Review;
import hibernatetutorial.section_27_manymany.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DeleteACourseDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.coursestudent.xml";
    private static final String COURSE_TITLE_LOOKUP = "%Atari%";


    public static void main(String[] args) {

        Course course;

        SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Review.class)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            // Deleting a course will not delete associated student(s) - we've set correct CascadeType
            course = session
                        .createQuery("FROM Course c WHERE c.title LIKE :title", Course.class)
                        .setParameter("title", COURSE_TITLE_LOOKUP)
                        .uniqueResult();

            if (course != null) {
                System.out.println("Deleting course: " + course.getTitle());
                System.out.println("Join table will be updated accordingly");
                System.out.println("This will not delete associated student(s)...");

                session.delete(course);
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
