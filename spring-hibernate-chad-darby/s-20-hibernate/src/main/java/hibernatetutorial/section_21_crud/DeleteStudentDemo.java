package hibernatetutorial.section_21_crud;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import hibernatetutorial.section_21_crud.entity.Student;


public class DeleteStudentDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.student.xml";

    public static void main(String[] args) {

        int studentId = 12;
        Student aStudent;

        try (SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory()) {

            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            aStudent = session.get(Student.class, studentId);
            System.out.println("aStudent: " + aStudent);

//            // delete the row corresponding to aStudent information
            session.delete(aStudent);

            // we can also delete rows with a createQuery
            session.createQuery("delete from Student s where s.id = 12").executeUpdate();

            session.getTransaction().commit();
        }

        // although we've deleted the row from DB, all information remains in the aStudent
        System.out.println("aStudent: " + aStudent);
    }
}