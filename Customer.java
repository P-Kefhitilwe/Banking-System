public abstract class Customer {
    protected String name;
    protected String identifier; // ID or registration number

    public Customer(String name, String identifier) {
        this.name = name;
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public abstract String getCustomerType();

    @Override
    public String toString() {
        return getCustomerType() + ": " + name + " (" + identifier + ")";
    }
}
