package io.spring.batch.processes;

import io.spring.batch.domain.AccountSummary;
import io.spring.batch.domain.Transaction;
import io.spring.batch.domain.dao.TransactionDao;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

@AllArgsConstructor
public class TransactionApplierProcessor implements ItemProcessor<AccountSummary, AccountSummary> {

    private TransactionDao transactionDao;

    public AccountSummary process(AccountSummary summary) {
        List<Transaction> transactions = transactionDao.getTransactionsByAccountNumber(summary.getAccountNumber());

        for (Transaction transaction : transactions) {
            summary.setCurrentBalance(summary.getCurrentBalance() + transaction.getAmount());
        }
        return summary;
    }
}
