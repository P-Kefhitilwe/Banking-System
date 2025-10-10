package banking;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all bank accounts.
 */
public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected List<String> transactionHistory;
    protected static final String CURRENCY = "BWP";

    public Account(String accountNumber, double initialDeposit) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be blank.");
        }
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative.");
        }

        this.accountNumber = accountNumber;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        recordTransaction("Account created with balance: " + balance + " " + CURRENCY);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    protected void recordTransaction(String details) {
        transactionHistory.add(details);
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive.");
        balance += amount;
        recordTransaction("Deposited: " + amount + " " + CURRENCY);
    }

    public abstract void withdraw(double amount);
}
