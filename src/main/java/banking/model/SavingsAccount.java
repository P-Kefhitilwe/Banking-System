package banking.model;

public class SavingsAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.0005; // 0.05% monthly

    public SavingsAccount() {
        super();
    }

    public SavingsAccount(String accountNumber, double initialBalance, Customer owner, String branch) {
        super(accountNumber, initialBalance, owner, branch);
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public double calculateInterest() {
        return balance * INTEREST_RATE;
    }

    @Override
    public void applyInterest() {
        double interest = calculateInterest();
        balance += interest;
        Transaction transaction = new Transaction(
                accountNumber,
                Transaction.TransactionType.INTEREST,
                interest,
                balance,
                "Monthly interest applied"
        );
        transactions.add(transaction);
    }

    @Override
    public double getInterestRate() {
        return INTEREST_RATE;
    }
}
