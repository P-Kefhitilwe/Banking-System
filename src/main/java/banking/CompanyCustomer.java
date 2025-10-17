<<<<<<< HEAD
package banking;

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
=======
package banking;

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
>>>>>>> 35cd528c21c5ef19a399326c06cf8cde5a587a42
