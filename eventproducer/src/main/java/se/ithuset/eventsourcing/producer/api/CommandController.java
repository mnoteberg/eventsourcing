package se.ithuset.eventsourcing.producer.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.ithuset.eventsourcing.producer.model.CreateAccountCommand;
import se.ithuset.eventsourcing.producer.service.EventService;

@RestController
public class CommandController {

    @Autowired
    private EventService service;

    @PostMapping("/accounts")
    public void createAccount(@RequestBody CreateAccountCommand command) throws JsonProcessingException {
        service.createAccount(command);
    }

    @PutMapping("/accounts/{accountId}/deposit/{amount}")
    public void depositMoney(@PathVariable("accountId") String accountId, @PathVariable("amount") int amount) throws JsonProcessingException {
        service.depositMoney(accountId, amount);
    }

    @PutMapping("/accounts/{accountId}/withdraw/{amount}")
    public void withdrawMoney(@PathVariable("accountId") String accountId, @PathVariable("amount") int amount) throws JsonProcessingException {
        service.withdrawMoney(accountId, amount);
    }

    @DeleteMapping("/accounts/{accountId}")
    public void closeAccount(@PathVariable("accountId") String accountId) throws JsonProcessingException {
        service.closeAccount(accountId);
    }
}