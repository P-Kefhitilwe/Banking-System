package banking.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected int accountId;
    protected String accountNumber;
    protected double balance;
    protected LocalDate dateOpened;
    protected LocalDate lastInterestDate;
    protected Customer owner;
    protected String branch;
    protected List<Transaction> transactions;

    public Account() {
        this.dateOpened = LocalDate.now();
        this.lastInterestDate = null;
        this.transactions = new ArrayList<>();
    }

    public Account(String accountNumber, double initialBalance, Customer owner, String branch) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.owner = owner;
        this.branch = branch;
        this.dateOpened = LocalDate.now();
        this.lastInterestDate = null;
        this.transactions = new ArrayList<>();
    }

    public abstract String getAccountType();

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        Transaction transaction = new Transaction(
                accountNumber,
                Transaction.TransactionType.DEPOSIT,
                amount,
                balance,
                "Deposit"
        );
        transactions.add(transaction);
    }

    // Check if account is eligible for interest (30-day rule)
    public boolean isEligibleForInterest() {
        if (lastInterestDate == null) {
            return true; // Never received interest before
        }
        // Check if at least 30 days have passed since last interest
        return LocalDate.now().isAfter(lastInterestDate.plusDays(30));
    }

    // Getters and Setters
    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public LocalDate getDateOpened() { return dateOpened; }
    public void setDateOpened(LocalDate dateOpened) { this.dateOpened = dateOpened; }

    public LocalDate getLastInterestDate() { return lastInterestDate; }
    public void setLastInterestDate(LocalDate lastInterestDate) { this.lastInterestDate = lastInterestDate; }

    public Customer getOwner() { return owner; }
    public void setOwner(Customer owner) { this.owner = owner; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    @Override
    public String toString() {
        return String.format("%s Account %s - Balance: BWP %.2f",
                getAccountType(), accountNumber, balance);
    }
}
