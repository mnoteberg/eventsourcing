package se.ithuset.eventsourcing.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;
import se.bank.event.AccountClosed;
import se.bank.event.AccountCreated;
import se.bank.event.EventType;
import se.bank.event.MoneyDeposited;
import se.bank.event.MoneyWithdrawn;
import se.ithuset.eventsourcing.service.AccountService;
import se.ithuset.eventsourcing.service.TransactionService;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Component
public class EventConsumer implements ConsumerSeekAware {
    private AccountService accountService;

    private TransactionService transactionService;

    private ObjectMapper objectMapper;

    private ConsumerSeekCallback callback;

    @Autowired
    public EventConsumer(AccountService accountService, TransactionService transactionService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "lab")
    public void listen(ConsumerRecord consumerRecord) throws IOException {
        System.out.println(consumerRecord);

        LocalDateTime timestamp = Instant.ofEpochMilli(consumerRecord.timestamp()).atZone(ZoneId.systemDefault()).toLocalDateTime();

        switch (EventType.valueOf((String) consumerRecord.key())) {
            case ACCOUNT_CREATED:
                accountService.createAccount(objectMapper.readValue((String) consumerRecord.value(), AccountCreated.class));
                break;
            case MONEY_DEPOSITED:
                MoneyDeposited moneyDeposited = objectMapper.readValue((String) consumerRecord.value(), MoneyDeposited.class);
                accountService.depositMoney(moneyDeposited);
                transactionService.depositMoney(moneyDeposited, timestamp);
                break;
            case MONEY_WITHDRAWN:
                MoneyWithdrawn moneyWithdrawn = objectMapper.readValue((String) consumerRecord.value(), MoneyWithdrawn.class);
                accountService.withdrawMoney(moneyWithdrawn);
                transactionService.withdrawMoney(moneyWithdrawn, timestamp);
                break;
            case ACCOUNT_CLOSED:
                accountService.closeAccount(objectMapper.readValue((String) consumerRecord.value(), AccountClosed.class));
                break;
            case INTEREST_CALCULATED:
                accountService.calculateInterest(timestamp);
                break;
        }
    }

    @Override
    public void registerSeekCallback(ConsumerSeekCallback callback) {
        System.out.println("EventConsumer.registerSeekCallback");
        this.callback = callback;
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        System.out.println("EventConsumer.onPartitionsAssigned");
    }

    @Override
    public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        System.out.println("EventConsumer.onIdleContainer");
    }

    public void replay(int offset) {
        callback.seek("lab", 0, offset);
    }
}