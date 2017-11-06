package se.ithuset.eventsourcing.model;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime timestamp;
    private TransactionType type;
    private int amount;

    public Transaction(LocalDateTime timestamp, TransactionType type, int amount) {
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" + "timestamp=" + timestamp +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }
}
