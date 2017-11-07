package se.ithuset.eventsourcing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.bank.event.AccountClosed;
import se.bank.event.AccountCreated;
import se.bank.event.EventType;
import se.bank.event.InterestCalculated;
import se.bank.event.MoneyDeposited;
import se.bank.event.MoneyWithdrawn;
import se.ithuset.eventsourcing.model.CreateAccountCommand;
import se.ithuset.eventsourcing.producer.EventProducer;

@Service
public class EventService {
    private EventProducer producer;

    private ObjectMapper objectMapper;

    private RestTemplate restTemplate;

    @Autowired
    public EventService(EventProducer producer, ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.producer = producer;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public void createAccount(CreateAccountCommand command) throws JsonProcessingException {
        AccountCreated accountCreated = new AccountCreated(command.getCustomerName(), command.getEmail());
        producer.send(EventType.ACCOUNT_CREATED, objectMapper.writeValueAsString(accountCreated));
    }

    public void depositMoney(String accountId, int amount) throws JsonProcessingException {
        MoneyDeposited moneyDeposited = new MoneyDeposited(accountId, amount);
        producer.send(EventType.MONEY_DEPOSITED, objectMapper.writeValueAsString(moneyDeposited));
    }

    public void withdrawMoney(String accountId, int amount) throws JsonProcessingException {
        if (getBalance(accountId) < amount) {
            throw new IllegalStateException("Insufficient funds");
        }
        MoneyWithdrawn moneyWithdrawn = new MoneyWithdrawn(accountId, amount);
        producer.send(EventType.MONEY_WITHDRAWN, objectMapper.writeValueAsString(moneyWithdrawn));
    }

    public void closeAccount(String accountId) throws JsonProcessingException {
        if (getBalance(accountId) != 0) {
            throw new IllegalStateException("Illegal balance");
        }
        AccountClosed accountClosed = new AccountClosed(accountId);
        producer.send(EventType.ACCOUNT_CLOSED, objectMapper.writeValueAsString(accountClosed));
    }

    public void calculateInterest() throws JsonProcessingException {
        producer.send(EventType.INTEREST_CALCULATED, objectMapper.writeValueAsString(new InterestCalculated()));
    }

    private int getBalance(String accountId) {
        return restTemplate.getForObject("http://localhost:8080/accounts/{accountId}/balance", Integer.class, accountId);
    }
}
