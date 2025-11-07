package banking.model;

public class ChequeAccount extends Account implements Withdrawable {
    private String employerName;
    private String employerAddress;

    public ChequeAccount() {
        super();
    }

    public ChequeAccount(String accountNumber, double initialBalance, Customer owner,
                         String branch, String employerName, String employerAddress) {
        super(accountNumber, initialBalance, owner, branch);
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    @Override
    public String getAccountType() {
        return "Cheque";
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            return false; // Insufficient funds
        }
        balance -= amount;
        Transaction transaction = new Transaction(
                accountNumber,
                Transaction.TransactionType.WITHDRAWAL,
                amount,
                balance,
                "Withdrawal"
        );
        transactions.add(transaction);
        return true;
    }

    @Override
    public double getAvailableBalance() {
        return balance;
    }

    // Getters and Setters for employer details
    public String getEmployerName() { return employerName; }
    public void setEmployerName(String employerName) { this.employerName = employerName; }

    public String getEmployerAddress() { return employerAddress; }
    public void setEmployerAddress(String employerAddress) { this.employerAddress = employerAddress; }
}
