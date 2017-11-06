package se.ithuset.eventsourcing.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import se.ithuset.eventsourcing.model.Transaction;

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
}
