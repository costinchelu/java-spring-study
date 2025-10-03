package h2rest.repositories;

import model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class AccountRepository {

    public Optional<Account> findAccount(Connection con, int id) {
        String select = "SELECT * FROM account WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(select); ) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                var amount = rs.getBigDecimal("amount");

                Account account = new Account();
                account.setId(id);
                account.setAmount(amount);

                return Optional.of(account);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }


    public void updateAccount(Connection con, Account accountToUpdate) {
        String update = "UPDATE account SET amount = ? WHERE id = ?";

        try ( PreparedStatement stmt = con.prepareStatement(update); ){

            stmt.setBigDecimal(1, accountToUpdate.getAmount());
            stmt.setInt(2, accountToUpdate.getId());

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
