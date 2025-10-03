package hibernatetutorial.section_24_onemany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import hibernatetutorial.section_24_onemany.entity.Course;
import hibernatetutorial.section_24_onemany.entity.Instructor;
import hibernatetutorial.section_24_onemany.entity.InstructorDetail;


public class CreateInstructorDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.course.xml";

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
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            session.save(instructor1);
            session.save(instructor2);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
}
