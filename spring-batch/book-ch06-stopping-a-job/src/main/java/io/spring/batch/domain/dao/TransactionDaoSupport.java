package io.spring.batch.domain.dao;


import io.spring.batch.domain.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoSupport extends JdbcTemplate implements TransactionDao {

    public TransactionDaoSupport(DataSource dataSource) {
        super(dataSource);
    }

    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        return query(
                "select t.id, t.timestamp, t.amount " +
                        "from transaction t inner join account_summary a on " +
                        "a.id = t.account_summary_id " +
                        "where a.account_number = ?",
                new Object[] { accountNumber },
                new int[1],
                rs -> {
                    List<Transaction> list = new ArrayList<>();
                    while (rs.next()) {
                        Transaction trans = new Transaction();
                        trans.setAmount(rs.getDouble("amount"));
                        trans.setTimestamp(rs.getDate("timestamp"));
                        list.add(trans);
                    }
                    return list;
                }
        );
    }
}

