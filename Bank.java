import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts = new HashMap<>();
    private Map<String, Customer> customers = new HashMap<>();

    public void registerCustomer(Customer customer) {
        customers.put(customer.getIdentifier(), customer);
    }

    public void openAccount(Customer customer, String type, String accountNumber, double initialDeposit) {
        Account account;
        switch (type.toLowerCase()) {
            case "savings" -> account = new SavingsAccount(accountNumber, initialDeposit);
            case "investment" -> account = new InvestmentAccount(accountNumber, initialDeposit);
            case "cheque" -> account = new ChequeAccount(accountNumber, initialDeposit);
            default -> throw new IllegalArgumentException("Invalid account type.");
        }
        accounts.put(accountNumber, account);
        registerCustomer(customer);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public Customer getCustomer(String identifier) {
        return customers.get(identifier);
    }

    public void processMonthlyInterest() {
        for (Account acc : accounts.values()) {
            if (acc instanceof InterestBearing interestAcc) {
                interestAcc.calculateMonthlyInterest();
            }
        }
    }
}
