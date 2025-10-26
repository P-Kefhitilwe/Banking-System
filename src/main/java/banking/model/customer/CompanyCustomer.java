package banking.model.customer;

/**
 * Represents a company customer identified by registration number.
 */
public class CompanyCustomer extends Customer {

    public CompanyCustomer(String companyName, String registrationNumber) {
        super(companyName, registrationNumber);
    }

    @Override
    public String getCustomerType() {
        return "Company Customer";
    }
}
