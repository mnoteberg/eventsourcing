package se.ithuset.eventsourcing.producer.sceduling;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.ithuset.eventsourcing.producer.service.EventService;

@Component
public class ScheduledTasks {
    private EventService service;

    @Autowired
    public ScheduledTasks(EventService service) {
        this.service = service;
    }

    @Scheduled(initialDelay = 60_000, fixedRate = 60_000)
    public void calculateInterest() throws JsonProcessingException {
        System.out.println("Calculating interest...");
        service.calculateInterest();
    }
}