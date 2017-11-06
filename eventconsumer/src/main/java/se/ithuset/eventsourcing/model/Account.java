package se.ithuset.eventsourcing.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Account {
    private String accountId;
    private String customerName;
    private String email;
    private int balance;
    private Status status;

    public Account(String accountId, String customerName, String email) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.email = email;
        this.balance = 0;
        this.status = Status.ACTIVE;
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
