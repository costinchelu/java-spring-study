package io.spring.batch.domain.dao;

import io.spring.batch.domain.Transaction;

import java.util.List;


public interface TransactionDao {

    List<Transaction> getTransactionsByAccountNumber(String accountNumber);
}
