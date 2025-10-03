package springjdbc.entity;

import lombok.*;

import javax.persistence.Embeddable;

// @Embeddable means this class can be a part of another class
// without the need to create an Address table
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String line1;

    private String line2;

    private String city;
}
