package se.ithuset.eventsourcing.service;

import org.springframework.stereotype.Service;
import se.bank.event.AccountClosed;
import se.bank.event.AccountCreated;
import se.bank.event.MoneyDeposited;
import se.bank.event.MoneyWithdrawn;
import se.ithuset.eventsourcing.model.Account;
import se.ithuset.eventsourcing.model.Status;
import se.ithuset.eventsourcing.repository.AccountRepository;

import java.util.UUID;

@Service
public class AccountService {
    private AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public void createAccount(AccountCreated event) {
        repository.updateAccount(new Account(UUID.randomUUID().toString(), event.getCustomerName(), event.getEmail()));
    }

    public Account get(String accountId) {
        return getAccount(accountId);
    }

    public void depositMoney(MoneyDeposited moneyDeposited) {
        Account account = getAccount(moneyDeposited.getAccountId());
        account.setBalance(account.getBalance() + moneyDeposited.getAmount());
        repository.updateAccount(account);
    }

    public void withdrawMoney(MoneyWithdrawn moneyWithdrawn) {
        Account account = getAccount(moneyWithdrawn.getAccountId());
        account.setBalance(account.getBalance() - moneyWithdrawn.getAmount());
        repository.updateAccount(account);
    }

    public void closeAccount(AccountClosed accountClosed) {
        Account account = getAccount(accountClosed.getAccountId());
        if (account.getBalance() == 0) {
            account.setStatus(Status.INACTIVE);
            repository.updateAccount(account);
        } else {
            throw new IllegalStateException("Non-zero balanace: " + account.getBalance());
        }
    }

    public int getBalance(String accountId) {
        return getAccount(accountId).getBalance();
    }

    private Account getAccount(String accountId) {
        Account account = repository.getAccount(accountId);
        if (account == null) {
            throw new IllegalStateException("Account does not exist: " + accountId);
        }
        return account;
    }
}