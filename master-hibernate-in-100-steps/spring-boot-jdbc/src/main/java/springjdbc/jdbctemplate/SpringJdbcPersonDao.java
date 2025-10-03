package springjdbc.jdbctemplate;

import springjdbc.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class SpringJdbcPersonDao {

    private JdbcTemplate jdbcTemplate;

    // BeanPropertyRowMapper maps the results of the query to the Person bean
    public List<Person> findAll() {
        return jdbcTemplate.query("SELECT * FROM person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id = ?",
                new BeanPropertyRowMapper<>(Person.class),
                id);
    }

    public List<Person> findByName(String[] names) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name IN (?, ?)",
                new BeanPropertyRowMapper<>(Person.class),
                (Object[]) names);
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM person WHERE id = ?",
                id);
    }

    public int insertPerson(Person p) {
        return jdbcTemplate.update("INSERT INTO person (id, name, location, birth_date) VALUES (?, ?, ?, ?)",
                p.getId(), p.getName(), p.getLocation(), p.getBirthDate());
    }

    public int updatePerson(Person p) {
        return jdbcTemplate.update("UPDATE person SET name = ?, location = ? WHERE id = ?",
                p.getName(), p.getLocation(), p.getId());
    }

    // if data coming from the query is of a different structured compared to Person bean, we can create our own rowMapper
    public List<Person> findAllWithCustomRowMapper() {
        return jdbcTemplate.query("SELECT id, name, birth_date FROM person",
                (rs, rowNum) ->
                        Person.builder()
                                .id(rs.getInt("id"))
                                .name(rs.getString("name"))
                                .birthDate(rs.getTimestamp("birth_date").toLocalDateTime())
                                .build());
    }

    // using ResultSetExtractor
    public List<Person> findByNameExtractor(String[] names) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name IN (?, ?)",
                new ResultSetExtractor<>() {

                    @Override
                    public List<Person> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        List<Person> people = new ArrayList<>();
                        while (rs.next()) {
                            Person p = Person.builder()
                                    .id(rs.getInt("id"))
                                    .name(rs.getString("name"))
                                    .birthDate(rs.getTimestamp("birth_date").toLocalDateTime())
                                    .build();
                            people.add(p);
                        }
                        return people;
                    }
                },
                (Object[]) names);
    }
}
