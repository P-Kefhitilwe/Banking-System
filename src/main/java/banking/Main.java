public package banking;

/**
 * Test runner for the Banking System domain logic.
 */
public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Customer ind = new IndividualCustomer("Alice M.", "ID12345");
        Customer comp = new CompanyCustomer("Acme Corp", "BW001");

        bank.openAccount(ind, "savings", "SAV-001", 100.0);
        bank.openAccount(comp, "cheque", "CHQ-001", 200.0);

        Account a1 = bank.getAccount("SAV-001");
        a1.deposit(50);
        System.out.println(a1.getBalance());

        Account a2 = bank.getAccount("CHQ-001");
        a2.withdraw(50);
        System.out.println(a2.getBalance());

        System.out.println(bank.getCustomer("ID12345"));
        System.out.println(bank.getCustomer("BW001"));

        bank.processMonthlyInterest();
        System.out.println("After interest: " + a1.getBalance());

        System.out.println("\n--- Transaction History (Savings) ---");
        for (String log : a1.getTransactionHistory()) {
            System.out.println(log);
        }
    }
}
 Main {
    
}
