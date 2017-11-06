package se.ithuset.eventsourcing.service;

import org.springframework.stereotype.Service;
import se.bank.event.AccountCreated;
import se.bank.event.MoneyDeposited;
import se.ithuset.eventsourcing.model.Account;
import se.ithuset.eventsourcing.repository.BankRepository;

import java.util.UUID;

@Service
public class BankService {
    private BankRepository repository;

    public BankService(BankRepository repository) {
        this.repository = repository;
    }

    public void createAccount(AccountCreated event) {
        repository.updateAccount(new Account(UUID.randomUUID().toString(), event.getCustomerName(), event.getEmail()));
    }

    public void depositMoney(MoneyDeposited moneyDeposited) {
        Account account = repository.getAccount(moneyDeposited.getAccountId());
        account.setBalance(account.getBalance() + moneyDeposited.getAmount());
        repository.updateAccount(account);
    }
}
