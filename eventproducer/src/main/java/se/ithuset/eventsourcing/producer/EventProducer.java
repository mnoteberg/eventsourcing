package se.ithuset.eventsourcing.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import se.bank.event.EventType;

@Component
public class EventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public EventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(EventType type, String event) {
        System.out.println("type = [" + type + "], event = [" + event + "]");
        kafkaTemplate.send("lab", type.name(), event);
    }
}