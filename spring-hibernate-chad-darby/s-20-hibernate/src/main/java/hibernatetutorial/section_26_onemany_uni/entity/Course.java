package hibernatetutorial.section_26_onemany_uni.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    // we need this to link instructor_id in course table to id of instructor (BIDIRECTIONAL RELATION)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    // we need this to link id in course table to course_id in review table (UNIDIRECTIONAL RELATION)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private List<Review> reviews;


    public Course() {
    }

    public Course(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Review> getReviews() {

        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


    // being an unidirectional relation from the course part, hibernate will automatically bind course_id to the course
    // column in the DB, in the moment we will add the review to a certain course
    public void addReview(Review review) {
        if(reviews == null) {
            reviews = new ArrayList<>();
        }
        reviews.add(review);
    }

    @Override
    public String toString() {
        return "[COURSE ID " + id + ": \"" + title + "\"]";
    }
}
