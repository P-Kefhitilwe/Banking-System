<<<<<<< HEAD
package banking;

/**
 * Represents an individual customer identified by National ID.
 */
public class IndividualCustomer extends Customer {

    public IndividualCustomer(String name, String nationalId) {
        super(name, nationalId);
    }

    @Override
    public String getCustomerType() {
        return "Individual Customer";
    }
}
=======
package banking;

/**
 * Represents an individual customer identified by National ID.
 */
public class IndividualCustomer extends Customer {

    public IndividualCustomer(String name, String nationalId) {
        super(name, nationalId);
    }

    @Override
    public String getCustomerType() {
        return "Individual Customer";
    }
}
>>>>>>> 35cd528c21c5ef19a399326c06cf8cde5a587a42
