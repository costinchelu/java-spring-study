package hibernatetutorial.section_25_eagerlazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import hibernatetutorial.section_25_eagerlazy.entity.Course;
import hibernatetutorial.section_25_eagerlazy.entity.Instructor;
import hibernatetutorial.section_25_eagerlazy.entity.InstructorDetail;

public class DeleteCourseDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.course.xml";
    private static final int COURSE_TO_DELETE_ID = 12;

    public static void main(String[] args) {

        Course courseToDelete = null;

        SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            // get the course from DB:

            // variant 1:
            courseToDelete = session.get(Course.class, COURSE_TO_DELETE_ID);

            // variant 2:
//            Query query = session.createQuery("from Course c where c.title like '% 3'");
//            courseToDelete = (Course)query.list().get(0);

            System.out.println("Received course: " + courseToDelete);

            session.delete(courseToDelete);

            session.getTransaction().commit();

        } catch (Exception e) {
            session.close();
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}
