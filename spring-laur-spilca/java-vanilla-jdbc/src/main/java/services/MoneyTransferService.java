package services;

import db.DBConnectionManager;
import exceptions.AccountMissingException;
import model.Account;
import h2rest.repositories.AccountRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;


/**
 * A - Atomicity
 * C - Consistency
 * I - Isolation
 * D - Durability
 */
public class MoneyTransferService {

    private final AccountRepository accountRepository;


    public MoneyTransferService() {
        this.accountRepository = new AccountRepository();
    }

    public void transferMoney(int fromAccount, int toAccount, BigDecimal amountToTransfer) {
        try ( var con = DBConnectionManager.getInstance().getConnection() ) {

            Optional<Account> from = accountRepository.findAccount(con, fromAccount);
            Optional<Account> to = accountRepository.findAccount(con, toAccount);

            Account accountFrom = from.orElseThrow(AccountMissingException::new);
            Account accountTo = to.orElseThrow(AccountMissingException::new);


            accountFrom.setAmount(accountFrom.getAmount().subtract(amountToTransfer));
            accountTo.setAmount(accountTo.getAmount().add(amountToTransfer));

            accountRepository.updateAccount(con, accountFrom);
            // to test a problem with the transfer:
            // if (true) throw new RuntimeException(":(");
            accountRepository.updateAccount(con, accountTo);

            con.commit();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
