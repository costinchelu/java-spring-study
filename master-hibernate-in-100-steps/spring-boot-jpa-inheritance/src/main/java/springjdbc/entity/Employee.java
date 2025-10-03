package springjdbc.entity;

import lombok.*;

import javax.persistence.*;

/**
 * default strategy for inheritance is SINGLE_TABLE
 *
 * SINGLE_TABLE:
 * - just one table called EMPLOYEE
 * - no need for joins so more performance
 * - there will be columns for all the properties of the defined classes:
 *      ID  NAME    SALARY  HOURLY_WAGE     DTYPE
 * - both columns defined for subclasses will be nullable
 * - DTYPE (distinguished type - we can use @DiscriminatorColumn to define a name)
 *
 * TABLE_PER_CLASS
 * - a table for each CONCRETE entity class
 * - all common columns (from the superclass) are repeated for each table
 *
 * JOINED
 * - we will have a column for each class (also for the abstract Employee)
 * - it is efficient from the DB design perspective, but data is queried using JOIN (lower performance)
 *
 * @MappedSuperclass
 * - not an entity !!!
 * - mappings will only apply to subclasses
 * - separated tables are created for part and for full employees
 * - there will be no relation between those 2 tables
 * - they can be fetched individually
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "EmployeeType")
@NoArgsConstructor
@Getter
@Setter
public abstract class Employee {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    protected Employee(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
