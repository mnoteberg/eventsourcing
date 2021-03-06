package se.ithuset.eventsourcing.consumer.service;

import org.apache.kafka.common.metrics.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.bank.event.AccountClosed;
import se.bank.event.AccountCreated;
import se.bank.event.MoneyDeposited;
import se.bank.event.MoneyWithdrawn;
import se.ithuset.eventsourcing.consumer.model.Account;
import se.ithuset.eventsourcing.consumer.model.Status;
import se.ithuset.eventsourcing.consumer.model.TransactionType;
import se.ithuset.eventsourcing.consumer.repository.AccountRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService {
    private AccountRepository repository;
    private TransactionService transactionService;

    @Autowired
    public AccountService(AccountRepository repository, TransactionService transactionService) {
        this.repository = repository;
        this.transactionService = transactionService;
    }

    public void createAccount(AccountCreated event) {
        if (!repository.exists(event.getAccountId())) {
            repository.updateAccount(new Account(event.getAccountId(), event.getCustomerName(), event.getEmail()));
        }
    }

    public Account get(String accountId) {
        return getAccount(accountId);
    }

    public void depositMoney(MoneyDeposited moneyDeposited, LocalDateTime timestamp) {
        if (!transactionService.exists(moneyDeposited.getAccountId(), TransactionType.DEPOSIT, timestamp)) {
            Account account = getAccount(moneyDeposited.getAccountId());
            if (!Status.INACTIVE.equals(account.getStatus())) {
                account.setBalance(account.getBalance() + moneyDeposited.getAmount());
                repository.updateAccount(account);
                transactionService.depositMoney(moneyDeposited, timestamp);
            }
        }
    }

    public void withdrawMoney(MoneyWithdrawn moneyWithdrawn, LocalDateTime timestamp) {
        if (!transactionService.exists(moneyWithdrawn.getAccountId(), TransactionType.WITHDRAWAL, timestamp)) {
            Account account = getAccount(moneyWithdrawn.getAccountId());
            account.setBalance(account.getBalance() - moneyWithdrawn.getAmount());
            repository.updateAccount(account);
            transactionService.withdrawMoney(moneyWithdrawn, timestamp);
        }
    }

    public void closeAccount(AccountClosed accountClosed) {
        Account account = repository.getAccount(accountClosed.getAccountId());
        if (account != null && !Status.INACTIVE.equals(account.getStatus())) {
            if (account.getBalance() == 0) {
                account.setStatus(Status.INACTIVE);
                repository.updateAccount(account);
            } else {
                throw new IllegalStateException("Non-zero balanace: " + account.getBalance());
            }
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

    public void calculateInterest(LocalDateTime timestamp) {
        repository.findAll().stream()
                .map(Account::calculateInterest)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(p -> {
                    repository.updateAccount(p.getLeft());
                    transactionService.interest(p.getLeft().getAccountId(), p.getRight(), timestamp);
                });
    }
}
