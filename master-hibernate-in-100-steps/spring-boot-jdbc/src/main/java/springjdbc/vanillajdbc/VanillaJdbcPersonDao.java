package springjdbc.vanillajdbc;

import springjdbc.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class VanillaJdbcPersonDao {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        try (
                Connection con = DriverManager.getConnection(url, user, password);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM person")) {

            while (rs.next()) {
                Person p = Person.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .location(rs.getString("location"))
                        .birthDate(rs.getTimestamp("birth_date").toLocalDateTime())
                        .build();

                persons.add(p);
            }
        } catch (SQLException e) {
            log.error("{} - {}" , e.getSQLState(), e.getMessage());
        }
        return persons;
    }

    public int insertPerson(Person newPerson) {
        try (
                Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement pSt = con.prepareStatement("INSERT INTO person (id, name, location, birth_date) VALUES (?, ?, ?, ?)")){

            pSt.setInt(1, newPerson.getId());
            pSt.setString(2, newPerson.getName());
            pSt.setString(3, newPerson.getLocation());
            pSt.setTimestamp(4, Timestamp.valueOf(newPerson.getBirthDate()));
            return pSt.executeUpdate();
        } catch (SQLException e) {
            log.error("{} - {}" , e.getSQLState(), e.getMessage());
            return 0;
        }
    }

    public int updatePerson(Person p) {
        try (
                Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement pSt = con.prepareStatement("UPDATE person SET name = ?, location = ? WHERE id = ?")) {

            pSt.setString(1, p.getName());
            pSt.setString(2, p.getLocation());
            pSt.setInt(3, p.getId());
            return pSt.executeUpdate();
        } catch (SQLException e) {
            log.error("{} - {}" , e.getSQLState(), e.getMessage());
            return 0;
        }
    }

    public int deleteById(int id) {
        try (
                Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement pSt = con.prepareStatement("DELETE FROM person WHERE id = ?")) {

            pSt.setInt(1, id);
            return pSt.executeUpdate();
        } catch (SQLException e) {
            log.error("{} - {}" ,e.getSQLState(), e.getMessage());
            return 0;
        }
    }

}
