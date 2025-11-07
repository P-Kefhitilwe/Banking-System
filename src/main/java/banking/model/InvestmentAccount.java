package banking.model;

public class InvestmentAccount extends Account implements InterestBearing, Withdrawable {
    private static final double INTEREST_RATE = 0.05; // 5% monthly
    private static final double MINIMUM_OPENING_BALANCE = 500.00;

    public InvestmentAccount() {
        super();
    }

    public InvestmentAccount(String accountNumber, double initialBalance, Customer owner, String branch) {
        super(accountNumber, initialBalance, owner, branch);
        if (initialBalance < MINIMUM_OPENING_BALANCE) {
            throw new IllegalArgumentException(
                    "Investment account requires minimum opening balance of BWP " + MINIMUM_OPENING_BALANCE
            );
        }
    }

    @Override
    public String getAccountType() {
        return "Investment";
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

    public static double getMinimumOpeningBalance() {
        return MINIMUM_OPENING_BALANCE;
    }
}
