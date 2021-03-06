package se.bank.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MoneyWithdrawn {
    private String accountId;
    private int amount;

    @JsonCreator
    public MoneyWithdrawn(@JsonProperty("accountId") String accountId, @JsonProperty("amount") int amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "MoneyWithdrawn{" +
                "accountId='" + accountId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
