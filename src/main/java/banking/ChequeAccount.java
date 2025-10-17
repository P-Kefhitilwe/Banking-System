<<<<<<< HEAD
package banking;

/**
 * Cheque account — allows withdrawals, no interest.
 */
public class ChequeAccount extends Account {

    public ChequeAccount(String accountNumber, double initialDeposit) {
        super(accountNumber, initialDeposit);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal must be positive.");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds.");
        balance -= amount;
        recordTransaction("Withdrew: " + amount + " " + CURRENCY);
    }
}
=======
package banking;

/**
 * Cheque account — allows withdrawals, no interest.
 */
public class ChequeAccount extends Account {

    public ChequeAccount(String accountNumber, double initialDeposit) {
        super(accountNumber, initialDeposit);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal must be positive.");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds.");
        balance -= amount;
        recordTransaction("Withdrew: " + amount + " " + CURRENCY);
    }
}
>>>>>>> 35cd528c21c5ef19a399326c06cf8cde5a587a42
