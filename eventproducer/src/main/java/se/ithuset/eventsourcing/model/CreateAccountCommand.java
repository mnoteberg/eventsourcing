package se.ithuset.eventsourcing.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAccountCommand {
    private String customerName;
    private String email;

    @JsonCreator
    public CreateAccountCommand(@JsonProperty("customerName") String customerName, @JsonProperty("email") String email) {
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
        return "CreateAccountCommand{" +
                "customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
