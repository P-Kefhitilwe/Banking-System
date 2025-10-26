package banking;

import banking.model.Bank;
import banking.model.customer.IndividualCustomer;
import banking.model.customer.CompanyCustomer;
import banking.model.account.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the banking system.
 * Contains test cases for the Bank class and related functionality.
 */
public class BankTest {
    private Bank bank;
    
    @BeforeEach
    public void setUp() {
        bank = new Bank();
    }
    
    @Test
    public void testCreateIndividualCustomer() {
        var customer = new IndividualCustomer("Alice M.", "ID12345");
        assertNotNull(customer);
        assertEquals("Alice M.", customer.getName());
        assertEquals("ID12345", customer.getIdentifier());
    }
    
    @Test
    public void testCreateCompanyCustomer() {
        var customer = new CompanyCustomer("Acme Corp", "REG98765");
        assertNotNull(customer);
        assertEquals("Acme Corp", customer.getName());
        assertEquals("REG98765", customer.getIdentifier());
    }
    
    // Add more test cases as needed
}
