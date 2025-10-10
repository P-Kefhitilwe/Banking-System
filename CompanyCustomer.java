public class CompanyCustomer extends Customer {

    public CompanyCustomer(String companyName, String registrationNumber) {
        super(companyName, registrationNumber);
    }

    @Override
    public String getCustomerType() {
        return "Company Customer";
    }
}
