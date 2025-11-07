package banking.model;

public interface Withdrawable {
    boolean withdraw(double amount);
    double getAvailableBalance();
}
