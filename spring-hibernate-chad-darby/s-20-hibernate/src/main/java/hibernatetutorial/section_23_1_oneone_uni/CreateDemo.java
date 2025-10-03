package hibernatetutorial.section_23_1_oneone_uni;

import hibernatetutorial.section_23_1_oneone_uni.entity.Instructor;
import hibernatetutorial.section_23_1_oneone_uni.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class CreateDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.instructor.xml";

    public static void main(String[] args) {

        InstructorDetail instructorDetail1 =
                new InstructorDetail("http://www.luv2code.com/youtube", "Luv 2 code!");
        InstructorDetail instructorDetail2 =
                new InstructorDetail("http://www.luv2code.com/youtube", "Wolves");

        Instructor instructor1 = new Instructor("Chad", "Darby", "darby@luv2code.com");
        Instructor instructor2 = new Instructor("Jon", "Snow", "snow@luv2code.com");

        instructor1.setInstructorDetail(instructorDetail1);
        instructor2.setInstructorDetail(instructorDetail2);


        SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            // save instructor
            // this will ALSO save InstructorDetails object because of CascadeType.ALL
            // ALSO hibernate will modify id's of saved objects to be synchronized with the DB
            session.save(instructor1);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }

        System.out.println("Instructor saved:");
        System.out.println(instructor1);
        System.out.println(instructor2);
    }
}
