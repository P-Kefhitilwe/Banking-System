public class IndividualCustomer extends Customer {

    public IndividualCustomer(String name, String nationalId) {
        super(name, nationalId);
    }

    @Override
    public String getCustomerType() {
        return "Individual Customer";
    }
}
