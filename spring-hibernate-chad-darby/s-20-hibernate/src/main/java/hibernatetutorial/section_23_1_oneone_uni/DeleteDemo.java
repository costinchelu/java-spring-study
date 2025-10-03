package hibernatetutorial.section_23_1_oneone_uni;

import hibernatetutorial.section_23_1_oneone_uni.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import hibernatetutorial.section_23_1_oneone_uni.entity.InstructorDetail;


public class DeleteDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.instructor.xml";

    public static void main(String[] args) {

        int instructorId = 100;
        Instructor instructor1 = null;

        try (SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory()) {

            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

           instructor1 = session.get(Instructor.class, instructorId);
            System.out.println("instructor1: " + instructor1);

//            // delete the row corresponding to instructor information (CascadeType.ALL -> will also delete details)
            session.delete(instructor1);

            session.getTransaction().commit();

        } catch (IllegalArgumentException e) {
            System.out.println("It seems the id provided for deletion is not valid id (no entry to delete)");
        }

        // although we've deleted the row from DB, all information remains in the instructor
        System.out.println("instructor1: " + instructor1);
    }
}
