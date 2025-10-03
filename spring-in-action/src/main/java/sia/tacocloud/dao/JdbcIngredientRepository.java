package sia.tacocloud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sia.tacocloud.model.Ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        String sqlQuery = "select id, name, type from Ingredient";
        return jdbc.query(sqlQuery, this::mapRowToIngredient);
    }

    public Ingredient findOne(String id) {
        String sqlQuery = "select id, name, type from Ingredient where id=?";
        Object[] args = new Object[]{id};
        return jdbc.queryForObject(sqlQuery, args, this::mapRowToIngredient);
    }

    public Ingredient alternativeFindOne(String id) {
        String sqlQuery = "select id, name, type from Ingredient where id=?";
        Object[] args = new Object[]{id};
        return jdbc.queryForObject(
                sqlQuery,
                args,
                new RowMapper<Ingredient>() {
                    @Override
                    public Ingredient mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                        return new Ingredient(
                                resultSet.getString("id"),
                                resultSet.getString("name"),
                                Ingredient.Type.valueOf(resultSet.getString("type"))
                        );
                    }
                });
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        String sqlQuery = "insert into Ingredient (id, name, type) values (?, ?, ?)";
        jdbc.update(
                sqlQuery,
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString()
                );
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }
}
