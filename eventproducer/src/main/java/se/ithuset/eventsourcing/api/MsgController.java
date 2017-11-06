package se.ithuset.eventsourcing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.bank.event.EventType;
import se.ithuset.eventsourcing.producer.EventProducer;

@RestController
public class MsgController {

    @Autowired
    private EventProducer bean;

    @PostMapping("/type/{type}")
    public void publishEvent(@PathVariable(value = "type") EventType type, @RequestBody String event) {
        bean.send(type, event);
    }
}