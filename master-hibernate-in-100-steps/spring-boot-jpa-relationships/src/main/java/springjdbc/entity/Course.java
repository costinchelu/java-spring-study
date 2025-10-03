package springjdbc.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// named queries can be used more than one time in the code without duplicating the original query
// @SQLDelete uses column is_deleted to be marked as true whenever we try to delete the row (soft delete)
// @Where will only return the rows that have is_deleted = false in a query (retrieve only  active courses)
// @Where is not applied to native queries or methods used by the Entity Manager
// (we can use a @PreRemove hook to manually set isDeleted to true)
@Entity
@Table(name = "course_details")
@NamedQueries(
        value = { @NamedQuery(name = "get_all_courses", query = "SELECT c FROM Course c"),
                @NamedQuery(name = "get_all_courses_join_fetch_students", query = "SELECT c FROM Course c JOIN FETCH c.students s"),
                @NamedQuery(name = "get_some_courses", query = "SELECT c FROM Course c WHERE c.id >= 3") }
)
@SQLDelete(sql = "UPDATE course_details SET is_deleted=true WHERE id=?")
@Where(clause = "is_deleted = false")
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

    // for soft delete
    @Column(name = "is_deleted", nullable = false)
    @ColumnDefault("false")
    private boolean isDeleted;


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

//    @PreRemove
//    private void preRemove() {
//        this.isDeleted = true;
//    }
}
