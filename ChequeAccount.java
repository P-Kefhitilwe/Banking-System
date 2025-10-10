public class ChequeAccount extends Account {

    public ChequeAccount(String accountNumber, double initialDeposit) {
        super(accountNumber, initialDeposit);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds.");
        balance -= amount;
        recordTransaction("Withdrew: " + amount + " " + CURRENCY);
    }
}
