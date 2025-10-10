public class Customer {
    private String name;
    private String identifier; // National ID or Registration Number
    private boolean isCompany;

    public Customer(String name, String identifier, boolean isCompany) {
        this.name = name;
        this.identifier = identifier;
        this.isCompany = isCompany;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isCompany() {
        return isCompany;
    }

    @Override
    public String toString() {
        return (isCompany ? "Company: " : "Individual: ") + name + " (" + identifier + ")";
    }
}
