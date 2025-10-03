package springjdbc.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries(
        value = { @NamedQuery(name = "get_all_students", query = "SELECT s FROM Student s"),
                @NamedQuery(name = "get_some_students", query = "SELECT s FROM Student s WHERE s.id >= 3") }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    // default fetch type in 1-1 relation is EAGER
    // it will not be useful in case we need the whole Student object for the toString method
    // @OneToOne(fetch = FetchType.LAZY)
    // case we delete the student, passport entry will also be deleted (cascade)
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Passport passport;

    // builder-default is used so that the Lombok builder initializes the collection
    @Builder.Default
    // because mappedBy is noted in the course table, then student table will own the relationship
    // (but it doesn't really matter who is the owning side in case of many-to-many)
    // we will use @JoinTable to customize the join table between student and course
    // joinColum -> name for the student_id foreign key
    // inverseJoinColumn -> name for the other foreign_key representing the course_id
    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name="student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses = new ArrayList<>();

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();


    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}