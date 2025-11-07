package banking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:h2:./bankingdb;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static boolean initialized = false;

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("H2 Database Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        if (!initialized) {
            initializeDatabase(conn);
            initialized = true;
        }
        return conn;
    }

    private static void initializeDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // Check if tables already exist
            var rs = conn.getMetaData().getTables(null, null, "CUSTOMERS", null);
            if (rs.next()) {
                System.out.println("✅ Database already initialized.");
                return;
            }

            System.out.println("🔧 Initializing database schema...");

            // Create CUSTOMERS table
            stmt.execute(
                    "CREATE TABLE customers (" +
                            "customer_id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "name VARCHAR(100) NOT NULL, " +
                            "id_number VARCHAR(50) NOT NULL UNIQUE, " +
                            "email VARCHAR(100) NOT NULL UNIQUE, " +
                            "phone VARCHAR(30), " +
                            "address VARCHAR(200), " +
                            "password VARCHAR(100) NOT NULL)"
            );

            // Create ACCOUNTS table with last_interest_date column
            stmt.execute(
                    "CREATE TABLE accounts (" +
                            "account_id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "account_number VARCHAR(30) NOT NULL UNIQUE, " +
                            "balance DOUBLE NOT NULL, " +
                            "date_opened DATE, " +
                            "last_interest_date DATE, " +
                            "owner_id INT, " +
                            "branch VARCHAR(50), " +
                            "account_type VARCHAR(20) NOT NULL, " +
                            "employer_name VARCHAR(100), " +
                            "employer_address VARCHAR(200), " +
                            "FOREIGN KEY (owner_id) REFERENCES customers(customer_id))"
            );

            // Create TRANSACTIONS table
            stmt.execute(
                    "CREATE TABLE transactions (" +
                            "transaction_id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "account_number VARCHAR(30) NOT NULL, " +
                            "type VARCHAR(20) NOT NULL, " +
                            "amount DOUBLE NOT NULL, " +
                            "timestamp DATETIME NOT NULL, " +
                            "balance_after DOUBLE NOT NULL, " +
                            "description VARCHAR(200), " +
                            "FOREIGN KEY (account_number) REFERENCES accounts(account_number))"
            );

            System.out.println("✅ Database schema created successfully!");

            // Insert sample data
            stmt.execute(
                    "INSERT INTO customers (name, id_number, email, phone, address, password) VALUES " +
                            "('Alice Mokoena', 'BW123456', 'alice.m@example.com', '72123456', 'Gaborone', 'alicepass'), " +
                            "('Bafana Dube', 'BW987654', 'bafana.d@example.com', '73123456', 'Francistown', 'bafanapass'), " +
                            "('Carol Motsumi', 'BW564738', 'carol.m@example.com', '74123456', 'Molepolole', 'carolpass')"
            );

            System.out.println("✅ Sample data loaded successfully!");

        } catch (SQLException e) {
            System.err.println("❌ Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
