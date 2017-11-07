package se.ithuset.eventsourcing.consumer.listener;

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
import se.ithuset.eventsourcing.consumer.service.AccountService;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Component
public class EventListener implements ConsumerSeekAware {
    private AccountService accountService;

    private ObjectMapper objectMapper;

    private ConsumerSeekCallback callback;

    @Autowired
    public EventListener(AccountService accountService, ObjectMapper objectMapper) {
        this.accountService = accountService;
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
                accountService.depositMoney(moneyDeposited, timestamp);
                break;
            case MONEY_WITHDRAWN:
                MoneyWithdrawn moneyWithdrawn = objectMapper.readValue((String) consumerRecord.value(), MoneyWithdrawn.class);
                accountService.withdrawMoney(moneyWithdrawn, timestamp);
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
        System.out.println("EventListener.registerSeekCallback");
        this.callback = callback;
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        System.out.println("EventListener.onPartitionsAssigned");
    }

    @Override
    public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        System.out.println("EventListener.onIdleContainer");
    }

    public void replay(int offset) {
        callback.seek("lab", 0, offset);
    }
}