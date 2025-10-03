package hibernatetutorial.section_23_2_oneone_bi;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import hibernatetutorial.section_23_2_oneone_bi.entity.Instructor;
import hibernatetutorial.section_23_2_oneone_bi.entity.InstructorDetail;


public class DeleteInstructorDetailDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.instructor.xml";

    public static void main(String[] args) {

        int id = 6;
        InstructorDetail instructorDetail = null;

        SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            // get the detail by providing an id
            instructorDetail = session.get(InstructorDetail.class, id);


            // to safely delete details we need first to remove the associated object reference
            // else the db would have a erroneous foreign key
            // break bi-directional link
            instructorDetail.getInstructor().setInstructorDetail(null);
            // then we can safely delete just the details
            session.delete(instructorDetail);

            session.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            System.out.println("Can't find instructor_detail_id provided!");
        } finally {
            session.close();
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
