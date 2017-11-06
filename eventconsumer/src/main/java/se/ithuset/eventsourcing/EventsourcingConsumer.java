package se.ithuset.eventsourcing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventsourcingConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EventsourcingConsumer.class);


    public static void main(String[] args) {
        SpringApplication.run(EventsourcingConsumer.class, args);
    }
}
