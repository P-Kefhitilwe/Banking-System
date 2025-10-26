package banking.model;

import banking.model.customer.Customer;
import banking.model.customer.IndividualCustomer;
import banking.model.customer.CompanyCustomer;
import banking.model.account.Account;
import banking.model.account.ChequeAccount;
import banking.model.account.SavingsAccount;
import banking.model.account.InvestmentAccount;
import banking.model.InterestBearing;

import java.util.HashMap;
import java.util.Map;

/**
 * Core Bank class — manages customers and accounts.
 */
public class Bank {
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, Account> accounts = new HashMap<>();

    public void registerCustomer(Customer customer) {
        customers.put(customer.getIdentifier(), customer);
    }

    public Customer getCustomer(String identifier) {
        return customers.get(identifier);
    }

    public void openAccount(Customer customer, String type, String accountNumber, double initialDeposit) {
        Account account;
        switch (type.toLowerCase()) {
            case "savings" -> account = new SavingsAccount(accountNumber, initialDeposit);
            case "investment" -> account = new InvestmentAccount(accountNumber, initialDeposit);
            case "cheque" -> account = new ChequeAccount(accountNumber, initialDeposit);
            default -> throw new IllegalArgumentException("Invalid account type: " + type);
        }
        accounts.put(accountNumber, account);
        registerCustomer(customer);
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
