package hibernatetutorial.section_24_onemany;

import hibernatetutorial.section_24_onemany.entity.Course;
import hibernatetutorial.section_24_onemany.entity.Instructor;
import hibernatetutorial.section_24_onemany.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class GetInstructorCoursesDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.course.xml";
    private static final int CHAD_ID = 1;

    public static void main(String[] args) {

        Instructor instructorChad = null;

        SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            instructorChad = session.get(Instructor.class, CHAD_ID);

            System.out.println(">> Instructor Chad:");
            System.out.println(instructorChad);
            System.out.println(">> Chad's courses:");
            System.out.println(instructorChad.getCourses());

            session.getTransaction().commit();

        } catch (Exception e) {
            session.close();
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}
