package springjdbc.entity;

import lombok.*;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class FullTimeEmployee extends Employee {

    private BigDecimal salary;

    public FullTimeEmployee(String name, BigDecimal salary) {
        super(name);
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "FullTimeEmployee{" +
                "salary=" + salary +
                '}';
    }
}
