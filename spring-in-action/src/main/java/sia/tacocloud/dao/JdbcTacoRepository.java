package sia.tacocloud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sia.tacocloud.model.Ingredient;
import sia.tacocloud.model.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco design) {
        long tacoId = saveTacoInfo(design);
        design.setId(tacoId);
        for (Ingredient ingredient : design.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return null;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        String sqlQuery = "insert into Taco (name, createdAt) values (?, ?)";
        PreparedStatementCreator psc =
                new PreparedStatementCreatorFactory(sqlQuery, Types.VARCHAR, Types.TIMESTAMP)
                .newPreparedStatementCreator(
                        Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        String sqlQuery = "insert into Taco_Ingredients (taco, ingredient) values (?, ?)";
        jdbc.update(sqlQuery, tacoId, ingredient.getId());
    }

}
