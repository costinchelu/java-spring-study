package hibernatetutorial.section_25_eagerlazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import hibernatetutorial.section_25_eagerlazy.entity.Course;
import hibernatetutorial.section_25_eagerlazy.entity.Instructor;
import hibernatetutorial.section_25_eagerlazy.entity.InstructorDetail;


public class EagerLazyDemo {

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

            // method to get all courses into instructor by using a join query (even if we have a lazy initialisation)
//            Query<Instructor> query =
//                    session.createQuery("select i from Instructor i " +
//                            "join fetch i.courses where i.id = :theInstructorId", Instructor.class);
//
//            query.setParameter("theInstructorId", CHAD_ID);
//
//            instructorChad = query.getSingleResult();


            session.getTransaction().commit();

        } catch (Exception e) {
            session.close();
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }

        // because we've set fetch type to EAGER, hibernate will load all courses when we are loading instructor
        // data from db. On LAZY loading we can't access courses after we are closing our session
        System.out.println(">> Instructor Chad:");
        System.out.println(instructorChad);
        System.out.println(">> Chad's courses:");
        if(instructorChad.getCourses() != null) {
            System.out.println(instructorChad.getCourses());
        }
    }
}
