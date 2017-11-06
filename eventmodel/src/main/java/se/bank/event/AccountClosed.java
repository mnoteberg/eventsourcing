package se.bank.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountClosed {
    private String accountId;

    @JsonCreator
    public AccountClosed(@JsonProperty("accountId") String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "AccountClosed{" + "accountId='" + accountId + '\'' +
                '}';
    }
}
