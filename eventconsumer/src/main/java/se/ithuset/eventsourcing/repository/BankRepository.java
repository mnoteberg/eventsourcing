package se.ithuset.eventsourcing.repository;

import org.springframework.stereotype.Repository;
import se.ithuset.eventsourcing.model.Account;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BankRepository {
    private static final Map<String, Account> store = new HashMap<>();

    public void updateAccount(Account account) {
        store.put(account.getAccountId(), account);
        System.out.println(store.get(account.getAccountId()));
    }

    public Account getAccount(String accountId) {
        return store.get(accountId);
    }
}
