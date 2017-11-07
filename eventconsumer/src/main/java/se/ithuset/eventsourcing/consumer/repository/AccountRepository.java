package se.ithuset.eventsourcing.consumer.repository;

import org.springframework.stereotype.Repository;
import se.ithuset.eventsourcing.consumer.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountRepository {
    private static final Map<String, Account> store = new HashMap<>();

    public void updateAccount(Account account) {
        store.put(account.getAccountId(), account);
        System.out.println(store.get(account.getAccountId()));
    }

    public Account getAccount(String accountId) {
        return store.get(accountId);
    }

    public List<Account> findAll() {
        return new ArrayList<>(store.values());
    }
}
