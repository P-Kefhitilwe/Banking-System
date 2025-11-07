package banking.util.dao;

import banking.model.Transaction;
import banking.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    public void addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (account_number, type, amount, timestamp, balance_after, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, transaction.getAccountNumber());
            ps.setString(2, transaction.getType().name());
            ps.setDouble(3, transaction.getAmount());
            ps.setTimestamp(4, Timestamp.valueOf(transaction.getTimestamp()));
            ps.setDouble(5, transaction.getBalanceAfter());
            ps.setString(6, transaction.getDescription());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) transaction.setTransactionId(rs.getInt(1));
            }
        }
    }

    public List<Transaction> getTransactionsForAccount(String accountNumber) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction(
                            rs.getString("account_number"),
                            Transaction.TransactionType.valueOf(rs.getString("type")),
                            rs.getDouble("amount"),
                            rs.getDouble("balance_after"),
                            rs.getString("description")
                    );
                    t.setTransactionId(rs.getInt("transaction_id"));
                    t.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    transactions.add(t);
                }
            }
        }
        return transactions;
    }
}
