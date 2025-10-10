import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected List<String> transactionHistory;
    protected static final String CURRENCY = "BWP"; // Default to Pula

    public Account(String accountNumber, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        recordTransaction("Account created with initial balance: " + balance + " " + CURRENCY);
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
        if (amount <= 0)
            throw new IllegalArgumentException("Deposit amount must be positive.");
        balance += amount;
        recordTransaction("Deposited: " + amount + " " + CURRENCY);
    }

    public abstract void withdraw(double amount);
}
