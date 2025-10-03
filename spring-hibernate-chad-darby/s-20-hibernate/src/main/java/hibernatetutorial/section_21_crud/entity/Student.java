package hibernatetutorial.section_21_crud.entity;

import javax.persistence.*;
import java.util.Date;

import static hibernatetutorial.utils.DateUtils.formatDate;


@Entity
@Table(name = "student")
public class Student {

    // identity is the usual strategy to use with auto-increment feature of MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "grade")
    private float grade;


    public Student() {
    }

    public Student(String firstName, String lastName, Date dateOfBirth, String email, float grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "(" + id + ")" + " Student: " + firstName + " " + lastName + ", birth date: " + formatDate(dateOfBirth)
                 + ", email: " + email + ", GPA: " + grade;
    }
}
