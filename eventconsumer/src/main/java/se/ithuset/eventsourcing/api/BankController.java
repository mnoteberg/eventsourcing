package se.ithuset.eventsourcing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.ithuset.eventsourcing.service.BankService;

@RestController
public class BankController {
    @Autowired
    private BankService service;

    @GetMapping("/account/{accountId}/balance")
    public int checkBalance(@PathVariable("accountId") String accountId) {
        return service.getBalance(accountId);
    }
}
