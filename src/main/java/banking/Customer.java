<<<<<<< HEAD
package banking;

/**
 * Abstract Customer superclass — base for Individual and Company.
 */
public abstract class Customer {
    protected String name;
    protected String identifier; // National ID or Registration Number

    public Customer(String name, String identifier) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required.");
        if (identifier == null || identifier.isBlank()) throw new IllegalArgumentException("Identifier required.");
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
        return String.format("%s: %s (%s)", getCustomerType(), name, identifier);
    }
}
=======
package banking;

/**
 * Abstract Customer superclass — base for Individual and Company.
 */
public abstract class Customer {
    protected String name;
    protected String identifier; // National ID or Registration Number

    public Customer(String name, String identifier) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required.");
        if (identifier == null || identifier.isBlank()) throw new IllegalArgumentException("Identifier required.");
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
        return String.format("%s: %s (%s)", getCustomerType(), name, identifier);
    }
}
>>>>>>> 35cd528c21c5ef19a399326c06cf8cde5a587a42
