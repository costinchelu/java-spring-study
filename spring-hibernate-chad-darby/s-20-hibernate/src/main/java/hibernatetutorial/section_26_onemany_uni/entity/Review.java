package hibernatetutorial.section_26_onemany_uni.entity;

import javax.persistence.*;


@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "comment")
    private String comment;

    // being an unidirectional relation from the course part, hibernate will automatically bind course_id to the course
    // column in the DB, in the moment we will add the review to a certain course

    public Review() {
    }

    public Review(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "REVIEW ID: " + id + " (" + comment + ")";
    }
}
