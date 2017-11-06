package se.ithuset.eventsourcing.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import se.bank.event.AccountClosed;
import se.bank.event.AccountCreated;
import se.bank.event.EventType;
import se.bank.event.MoneyDeposited;
import se.bank.event.MoneyWithdrawn;
import se.ithuset.eventsourcing.service.BankService;

import java.io.IOException;

@Component
public class EventConsumer {
    private BankService service;

    private ObjectMapper objectMapper;

    @Autowired
    public EventConsumer(BankService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "lab")
    public void listen(ConsumerRecord consumerRecord) throws IOException {
        System.out.println(consumerRecord);
        switch (EventType.valueOf((String) consumerRecord.key())) {
            case ACCOUNT_CREATED:
                service.createAccount(objectMapper.readValue((String) consumerRecord.value(), AccountCreated.class));
                break;
            case MONEY_DEPOSITED:
                service.depositMoney(objectMapper.readValue((String) consumerRecord.value(), MoneyDeposited.class));
                break;
            case MONEY_WITHDRAWN:
                service.withdrawMoney(objectMapper.readValue((String) consumerRecord.value(), MoneyWithdrawn.class));
                break;
            case ACCOUNT_CLOSED:
                service.closeAccount(objectMapper.readValue((String) consumerRecord.value(), AccountClosed.class));
                break;
        }
    }
}