package springjdbc.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// named queries can be used more than one time in the code without duplicating the original query
@Entity
@Table(name = "course_details")
@NamedQueries(
        value = { @NamedQuery(name = "get_all_courses", query = "SELECT c FROM Course c"),
                @NamedQuery(name = "get_some_courses", query = "SELECT c FROM Course c WHERE c.id >= 3") }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    // builder-default is used so that the Lombok builder initializes the collection
    @Builder.Default
    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();

    // columns for storing timestamps (case we need to keep track of state changes) (Hibernate annotation)
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @CreationTimestamp
    @Column(name = "created_time")
    private LocalDateTime createdTime;


    public void addStudent(Student student) {
        students.add(student);
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
