package se.bank.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountCreated {
    private String customerName;
    private String email;

    @JsonCreator
    public AccountCreated(@JsonProperty("customerName") String customerName, @JsonProperty("email") String email) {
        this.customerName = customerName;
        this.email = email;
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
                "customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
