package se.ithuset.eventsourcing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventsourcingProducer {

    public static void main(String[] args) {
        SpringApplication.run(EventsourcingProducer.class, args);
    }
}
