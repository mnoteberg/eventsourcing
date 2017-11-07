package se.ithuset.eventsourcing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.ithuset.eventsourcing.model.Account;
import se.ithuset.eventsourcing.model.Transaction;
import se.ithuset.eventsourcing.service.AccountService;
import se.ithuset.eventsourcing.service.TransactionService;
import se.ithuset.eventsourcing.consumer.EventConsumer;

import java.util.List;

@RestController
public class BankController {
    private AccountService accountService;

    private TransactionService transactionService;

    private EventConsumer eventConsumer;

    @Autowired
    public BankController(AccountService accountService, TransactionService transactionService, EventConsumer eventConsumer) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.eventConsumer = eventConsumer;
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
        eventConsumer.replay(offset);
    }
}
