public class SavingsAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.025; // 0.025% monthly
    private static final double MIN_BALANCE = 50;

    public SavingsAccount(String accountNumber, double initialDeposit) {
        super(accountNumber, Math.max(initialDeposit, MIN_BALANCE));
    }

    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawals are not allowed from Savings accounts.");
    }

    @Override
    public void calculateMonthlyInterest() {
        double interest = balance * (INTEREST_RATE / 100);
        balance += interest;
        recordTransaction("Interest added: " + interest + " " + CURRENCY);
    }
}
