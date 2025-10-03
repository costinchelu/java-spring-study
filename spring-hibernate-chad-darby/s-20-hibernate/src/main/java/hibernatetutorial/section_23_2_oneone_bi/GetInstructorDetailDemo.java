package hibernatetutorial.section_23_2_oneone_bi;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import hibernatetutorial.section_23_2_oneone_bi.entity.Instructor;
import hibernatetutorial.section_23_2_oneone_bi.entity.InstructorDetail;


public class GetInstructorDetailDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.instructor.xml";

    public static void main(String[] args) {

        int id = 7;
        InstructorDetail instructorDetail = null;

        try (SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory()) {

            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            // get the detail by providing an id
            instructorDetail = session.get(InstructorDetail.class, id);

            session.getTransaction().commit();
        }

        // print the associated instructor:
        try {
            System.out.println("Got: " + instructorDetail);
            System.out.println("Associated instructor: " + instructorDetail.getInstructor());

        } catch (NullPointerException e) {
            System.out.println("Id provided is not in the DB (DB returns a null id)");
        }
    }
}
