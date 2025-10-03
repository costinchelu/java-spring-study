package hibernatetutorial.section_21_crud;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import hibernatetutorial.section_21_crud.entity.Student;

import java.text.ParseException;

import static hibernatetutorial.utils.DateUtils.parseDate;


public class CreateStudentDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.student.xml";

    public static void main(String[] args) {

        Student aStudent = null;
        try {
            aStudent = new Student("Rod", "Sanders",
                    parseDate("31/07/2000"), "rod.s@luv2code.com", 8.5f);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // create session factory
        SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();     // configure would work without specifying "hibernate.cfg.student.xml" but only for the default file name "hibernate.cfg.xml"

        // create a session
        Session session = sessionFactory.getCurrentSession();

        try {
            // start a transaction
            session.beginTransaction();

            // save the student object
            session.save(aStudent);

            // commit transaction
            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
}
