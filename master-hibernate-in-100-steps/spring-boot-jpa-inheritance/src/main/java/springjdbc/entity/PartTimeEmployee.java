package springjdbc.entity;

import lombok.*;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PartTimeEmployee extends Employee {

    private BigDecimal hourlyWage;


    public PartTimeEmployee(String name, BigDecimal hourlyWage) {
        super(name);
        this.hourlyWage = hourlyWage;
    }

    @Override
    public String toString() {
        return "PartTimeEmployee{" +
                "hourlyWage=" + hourlyWage +
                '}';
    }
}
