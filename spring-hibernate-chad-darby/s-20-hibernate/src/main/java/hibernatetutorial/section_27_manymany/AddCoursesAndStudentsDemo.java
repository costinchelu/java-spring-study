package hibernatetutorial.section_27_manymany;

import hibernatetutorial.section_27_manymany.entity.Course;
import hibernatetutorial.section_27_manymany.entity.Instructor;
import hibernatetutorial.section_27_manymany.entity.InstructorDetail;
import hibernatetutorial.section_27_manymany.entity.Review;
import hibernatetutorial.section_27_manymany.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class AddCoursesAndStudentsDemo {

    private static final String CONFIG_FILE = "hibernate.cfg.coursestudent.xml";
    private static final int COURSE_TO_GET_ID = 11;

    public static void main(String[] args) {

        Student student1 = new Student("John", "Doe", "doe_j@mail.com");
        Student student2 = new Student("Emile", "Sender", "sender_e@mail.com");
        Student student3 = new Student("Sarah", "Rupert", "rupert_s@mail.com");

        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);

        Course course1 = new Course("Course 1 - CS101");
        Course course2 = new Course("Course 2 - Physics Advanced");
        Course course3 = new Course("Course 3 - Flute Intermediate");
        Course course4 = new Course("Course 4 - Java Beginner");

        List<Course> courseList = new ArrayList<>();
        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);
        courseList.add(course4);

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

            System.out.println("Saving courses in DB");
            for (Course c : courseList) {
                session.save(c);
            }

            System.out.println("Saving students in DB");
            for (Student s : studentList) {
                session.save(s);
            }

            System.out.println("Adding students to courses");
            course1.addStudent(student1);
            course1.addStudent(student3);
            course2.addStudent(student2);
            course3.addStudent(student2);
            course3.addStudent(student3);
            course4.addStudents(studentList);

            session.getTransaction().commit();

        } catch (Exception e) {
            session.close();
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}
