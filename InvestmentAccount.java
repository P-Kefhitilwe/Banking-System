public class InvestmentAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.075; // 0.075% monthly
    private static final double MIN_BALANCE = 500;

    public InvestmentAccount(String accountNumber, double initialDeposit) {
        super(accountNumber, Math.max(initialDeposit, MIN_BALANCE));
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds.");
        balance -= amount;
        recordTransaction("Withdrew: " + amount + " " + CURRENCY);
    }

    @Override
    public void calculateMonthlyInterest() {
        double interest = balance * (INTEREST_RATE / 100);
        balance += interest;
        recordTransaction("Interest added: " + interest + " " + CURRENCY);
    }
}
