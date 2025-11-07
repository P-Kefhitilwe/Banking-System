package banking.util.dao;

import banking.model.Account;
import banking.model.Customer;
import banking.model.SavingsAccount;
import banking.model.InvestmentAccount;
import banking.model.ChequeAccount;
import banking.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public void addAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, balance, date_opened, last_interest_date, owner_id, branch, account_type, employer_name, employer_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, account.getAccountNumber());
            ps.setDouble(2, account.getBalance());
            ps.setDate(3, java.sql.Date.valueOf(account.getDateOpened()));
            ps.setDate(4, account.getLastInterestDate() != null ?
                    java.sql.Date.valueOf(account.getLastInterestDate()) : null);
            ps.setInt(5, account.getOwner().getCustomerId());
            ps.setString(6, account.getBranch());
            ps.setString(7, account.getAccountType());
            if (account instanceof ChequeAccount) {
                ps.setString(8, ((ChequeAccount) account).getEmployerName());
                ps.setString(9, ((ChequeAccount) account).getEmployerAddress());
            } else {
                ps.setString(8, null);
                ps.setString(9, null);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) account.setAccountId(rs.getInt(1));
            }
        }
    }

    public List<Account> getAccountsByCustomer(Customer customer) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE owner_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customer.getCustomerId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String type = rs.getString("account_type");
                    Account account;
                    switch (type) {
                        case "Savings":
                            account = new SavingsAccount(
                                    rs.getString("account_number"),
                                    rs.getDouble("balance"),
                                    customer,
                                    rs.getString("branch")
                            );
                            break;
                        case "Investment":
                            account = new InvestmentAccount(
                                    rs.getString("account_number"),
                                    rs.getDouble("balance"),
                                    customer,
                                    rs.getString("branch")
                            );
                            break;
                        case "Cheque":
                            account = new ChequeAccount(
                                    rs.getString("account_number"),
                                    rs.getDouble("balance"),
                                    customer,
                                    rs.getString("branch"),
                                    rs.getString("employer_name"),
                                    rs.getString("employer_address")
                            );
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown account type");
                    }
                    account.setAccountId(rs.getInt("account_id"));
                    account.setDateOpened(rs.getDate("date_opened").toLocalDate());

                    // Load last interest date if exists
                    java.sql.Date lastInterestDate = rs.getDate("last_interest_date");
                    if (lastInterestDate != null) {
                        account.setLastInterestDate(lastInterestDate.toLocalDate());
                    }

                    accounts.add(account);
                }
            }
        }
        return accounts;
    }

    public void updateAccount(Account account) throws SQLException {
        String sql = "UPDATE accounts SET balance = ?, last_interest_date = ? WHERE account_number = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, account.getBalance());
            ps.setDate(2, account.getLastInterestDate() != null ?
                    java.sql.Date.valueOf(account.getLastInterestDate()) : null);
            ps.setString(3, account.getAccountNumber());
            ps.executeUpdate();
        }
    }
}
