package main;

import services.MoneyTransferService;

import java.math.BigDecimal;

public class TransactionDemo {

    public static void main(String[] args) {

        MoneyTransferService moneyTransferService = new MoneyTransferService();
        moneyTransferService.transferMoney(2, 1, new BigDecimal(500));
    }
}
