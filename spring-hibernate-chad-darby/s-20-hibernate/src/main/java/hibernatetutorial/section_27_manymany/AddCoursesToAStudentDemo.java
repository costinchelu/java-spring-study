package hibernatetutorial.section_27_manymany;

import hibernatetutorial.section_27_manymany.entity.Course;
import hibernatetutorial.section_27_manymany.entity.Instructor;
import hibernatetutorial.section_27_manymany.entity.InstructorDetail;
import hibernatetutorial.section_27_manymany.entity.Review;
import hibernatetutorial.section_27_manymany.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class AddCoursesToAStudentDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.coursestudent.xml";
    private static final String LAST_NAME_LOOKUP = "Doe";


    public static void main(String[] args) {

        Student student;
        Course course5 = new Course("Course 5 - Solve Rubik's Cube");
        Course course6 = new Course("Course 6 - Atari 2600 - Game Development");
        Course course7 = new Course("Course 7 - New Course - Outdoor Photography");

        SessionFactory sessionFactory = new Configuration()
                .configure(CONFIG_FILE)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Review.class)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            // get the required student from db
            student = session
                      .createQuery("from Student s where s.lastName = :lastname", Student.class)
                      .setParameter("lastname", LAST_NAME_LOOKUP)
                      .uniqueResult();

            // associate some courses with that student (set relationship managed by hibernate)
            if (student != null) {
                course5.addStudent(student);
                course6.addStudent(student);
            }

            // save courses in DB
            session.save(course5);
            session.save(course6);
            session.save(course7);


            // select all students example:
//            List<Student> studentList = session
//                                    .createQuery("select firstName, lastName from Student", Student.class)
//                                    .getResultList();
//
//`
//            for(Student s : studentList) {
//                System.out.println(s);
//            }


            session.getTransaction().commit();

        } catch (Exception e) {
            session.close();
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}
