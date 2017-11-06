package se.ithuset.eventsourcing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.bank.event.MoneyDeposited;
import se.bank.event.MoneyWithdrawn;
import se.ithuset.eventsourcing.model.Transaction;
import se.ithuset.eventsourcing.model.TransactionType;
import se.ithuset.eventsourcing.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private TransactionRepository repository;

    @Autowired
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void depositMoney(MoneyDeposited moneyDeposited, LocalDateTime timestamp) {
        Transaction transaction = new Transaction(timestamp, TransactionType.DEPOSIT, moneyDeposited.getAmount());
        repository.add(moneyDeposited.getAccountId(), transaction);
    }

    public void withdrawMoney(MoneyWithdrawn moneyWithdrawn, LocalDateTime timestamp) {
        Transaction transaction = new Transaction(timestamp, TransactionType.WITHDRAWAL, moneyWithdrawn.getAmount());
        repository.add(moneyWithdrawn.getAccountId(), transaction);
    }

    public List<Transaction> get(String accountId) {

        return repository.get(accountId).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .collect(Collectors.toList());
    }
}

