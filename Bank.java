import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts = new HashMap<>();

    public void openAccount(String type, String accountNumber, double initialDeposit) {
        Account account;
        switch (type.toLowerCase()) {
            case "savings" -> account = new SavingsAccount(accountNumber, initialDeposit);
            case "investment" -> account = new InvestmentAccount(accountNumber, initialDeposit);
            case "cheque" -> account = new ChequeAccount(accountNumber, initialDeposit);
            default -> throw new IllegalArgumentException("Invalid account type.");
        }
        accounts.put(accountNumber, account);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void processMonthlyInterest() {
        for (Account acc : accounts.values()) {
            if (acc instanceof InterestBearing interestAcc) {
                interestAcc.calculateMonthlyInterest();
            }
        }
    }
}
