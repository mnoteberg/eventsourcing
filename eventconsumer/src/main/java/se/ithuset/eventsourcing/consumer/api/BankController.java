package se.ithuset.eventsourcing.consumer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.ithuset.eventsourcing.consumer.listener.EventListener;
import se.ithuset.eventsourcing.consumer.model.Account;
import se.ithuset.eventsourcing.consumer.model.Transaction;
import se.ithuset.eventsourcing.consumer.service.AccountService;
import se.ithuset.eventsourcing.consumer.service.TransactionService;

import java.util.List;

@RestController
public class BankController {
    private AccountService accountService;

    private TransactionService transactionService;

    private EventListener eventListener;

    @Autowired
    public BankController(AccountService accountService, TransactionService transactionService, EventListener eventListener) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.eventListener = eventListener;
    }

    @GetMapping("/accounts/{accountId}")
    public Account getAccount(@PathVariable("accountId") String accountId) {
        return accountService.get(accountId);
    }

    @GetMapping("/accounts/{accountId}/balance")
    public int checkBalance(@PathVariable("accountId") String accountId) {
        return accountService.getBalance(accountId);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public List<Transaction> getTransactions(@PathVariable("accountId") String accountId) {
        return transactionService.get(accountId);
    }

    @GetMapping("/accounts/replay/{offset}")
    public void replay(@PathVariable("offset") int offset) {
        eventListener.replay(offset);
    }
}
