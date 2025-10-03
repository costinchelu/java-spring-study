package hibernatetutorial.section_21_crud;

import hibernatetutorial.section_21_crud.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.text.ParseException;

import static hibernatetutorial.utils.DateUtils.parseDate;


public class ReadStudentDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.student.xml";

    public static void main(String[] args) {

        Student aStudent = null;
        try {
            aStudent = new Student("Stephen", "Khan",
                    parseDate("12/08/2000"), "stephen.k@luv2code.com", 7.90f);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Student bStudent;

        SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        // try with resources: (but it also works with try - finally)
        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            // save aStudent
            session.beginTransaction();
            session.save(aStudent);
            System.out.println("aStudent generated id = " + aStudent.getId());

            // read aStudent to bStudent (by aStudent id)
            bStudent = session.get(Student.class, aStudent.getId());
            session.getTransaction().commit();
        }

        System.out.println("bStudent -> " + bStudent.toString());
    }
}
