package se.ithuset.eventsourcing.consumer.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import se.ithuset.eventsourcing.consumer.model.Transaction;
import se.ithuset.eventsourcing.consumer.model.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TransactionRepository {
    private static final MultiValueMap<String, Transaction> store = new LinkedMultiValueMap<>();

    public void add(String accountId, Transaction transaction) {
        store.add(accountId, transaction);
    }

    public List<Transaction> get(String accountId) {
        return store.get(accountId);
    }

    public boolean exists(String accountId, TransactionType type, LocalDateTime timestamp) {
        return store.containsKey(accountId) && store.get(accountId).stream()
                .anyMatch(t -> t.getType().equals(type) && t.getTimestamp().equals(timestamp));
    }
}
