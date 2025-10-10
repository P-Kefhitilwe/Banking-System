package banking;

/**
 * Savings account — no withdrawals, earns monthly interest.
 */
public class SavingsAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.025; // 0.025% monthly
    private static final double MIN_BALANCE = 50;

    public SavingsAccount(String accountNumber, double initialDeposit) {
        super(accountNumber, validateDeposit(initialDeposit));
    }

    private static double validateDeposit(double deposit) {
        if (deposit < MIN_BALANCE) {
            throw new IllegalArgumentException("Minimum deposit for Savings Account is " + MIN_BALANCE + " BWP");
        }
        return deposit;
    }

    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawals not allowed for Savings Accounts.");
    }

    @Override
    public void calculateMonthlyInterest() {
        double interest = balance * (INTEREST_RATE / 100);
        balance += interest;
        recordTransaction("Interest added: " + interest + " " + CURRENCY);
    }
}
