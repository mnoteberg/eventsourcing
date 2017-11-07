package se.ithuset.eventsourcing.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public class Account {
    private String accountId;
    private String customerName;
    private String email;
    private int balance;
    private Status status;
    private Double interestRate;

    public Account(String accountId, String customerName, String email) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.email = email;
        this.balance = 0;
        this.status = Status.ACTIVE;
        this.interestRate = 0.10d;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Optional<Pair<Account, Integer>> calculateInterest() {
        if (interestRate != null && interestRate > 0) {
            Double interest = balance * interestRate;
            balance += interest.intValue();
            return Optional.of(Pair.of(this, interest.intValue()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("accountId", accountId)
                .append("customerName", customerName)
                .append("email", email)
                .append("balance", balance)
                .append("status", status)
                .toString();
    }
}
