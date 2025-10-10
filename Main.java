public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Customer ind = new IndividualCustomer("Alice M.", "ID12345");
        Customer comp = new CompanyCustomer("Acme Corp", "BW001");

        bank.registerCustomer(ind);
        bank.registerCustomer(comp);

        bank.openAccount(ind, "savings", "SAV-001", 100.0);
        bank.openAccount(comp, "cheque", "CHQ-001", 0.0);

        Account a1 = bank.getAccount("SAV-001");
        a1.deposit(50);
        System.out.println(a1.getBalance());

        Account a2 = bank.getAccount("CHQ-001");
        a2.deposit(200);
        a2.withdraw(50);
        System.out.println(a2.getBalance());

        // print customer representations
        System.out.println(bank.getCustomer("ID12345"));
        System.out.println(bank.getCustomer("BW001"));

        // run monthly interest (applies to interest-bearing accounts)
        bank.processMonthlyInterest();
        System.out.println("After interest: " + bank.getAccount("SAV-001").getBalance());
    }
}
