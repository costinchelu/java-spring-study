package services;

import model.Account;
import h2rest.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class TransferService {

    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public void transferMoney(long idSender, long idReceiver, BigDecimal amount) {
        Account sender = accountRepository.findAccountById(idSender);
        Account receiver = accountRepository.findAccountById(idReceiver);

        BigDecimal senderNewAmount = sender.getAmount().subtract(amount);
        BigDecimal receiverNewAmount = receiver.getAmount().add(amount);

        // accountRepository.changeAmount(idSender, senderNewAmount);
        // accountRepository.changeAmount(idReceiver, receiverNewAmount);

        firstTransfer(idSender, senderNewAmount);
        secondTransfer(idReceiver, receiverNewAmount);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAllAccounts();
    }

    public Account getAccountById(long id) {
        return accountRepository.findAccountById(id);
    }

    private void firstTransfer(long idSender, BigDecimal amount) {
        accountRepository.changeAmount(idSender, amount);
        // throw new RuntimeException("Something is wrong");
    }

    private void secondTransfer(long idReceiver, BigDecimal amount) {
        accountRepository.changeAmount(idReceiver, amount);
    }
}
