package insertdemo.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    @Id
    private Integer rollNumber;

    private String firstName;

    private String middleName;

    private String lastName;

    private String studentsClass;

    private String section;

    private String address;
}
