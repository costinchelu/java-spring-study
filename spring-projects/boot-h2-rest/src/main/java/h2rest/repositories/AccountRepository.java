package h2rest.repositories;

import model.Account;
import h2rest.repositories.mappers.AccountRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbc;

    private final AccountRowMapper mapper;

    public AccountRepository(JdbcTemplate jdbc, AccountRowMapper mapper) {
        this.jdbc = jdbc;
        this.mapper = mapper;
    }

    public Account findAccountById(long id) {
        String sql = "SELECT * FROM account WHERE id = ?";
        return jdbc.queryForObject(sql, mapper, id);
    }

    public List<Account> findAllAccounts() {
        String sql = "SELECT * FROM account";
        return jdbc.query(sql, mapper);
    }

    public void changeAmount(long id, BigDecimal amount) {
        String sql = "UPDATE account SET amount = ? WHERE id = ?";
        jdbc.update(sql, amount, id);
    }
}
