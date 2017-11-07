package se.bank.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountCreated {
    private String accountId;
    private String customerName;
    private String email;

    @JsonCreator
    public AccountCreated(@JsonProperty("accountId") String accountId, @JsonProperty("customerName") String customerName, @JsonProperty("email") String email) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.email = email;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "AccountCreated{" +
                "accountId='" + accountId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
