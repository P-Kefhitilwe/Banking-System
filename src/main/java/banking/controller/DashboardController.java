package banking.controller;

import banking.BankingApp;
import banking.model.*;
import banking.util.dao.AccountDAO;
import banking.util.dao.TransactionDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DashboardController {
    @FXML private Label welcomeLabel;
    @FXML private Label totalBalanceLabel;
    @FXML private Label accountCountLabel;
    @FXML private Label interestAccountsLabel;
    @FXML private TableView<Account> accountsTable;
    @FXML private TableColumn<Account, String> typeColumn;
    @FXML private TableColumn<Account, String> numberColumn;
    @FXML private TableColumn<Account, Double> balanceColumn;
    @FXML private TableColumn<Account, String> branchColumn;
    @FXML private TableColumn<Account, String> interestRateColumn;
    @FXML private TableColumn<Account, Void> actionsColumn;

    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();
    public static Customer currentCustomer;

    @FXML
    public void initialize() {
        // Setup table columns
        typeColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getAccountType()));
        numberColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getAccountNumber()));
        balanceColumn.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getBalance()));
        branchColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getBranch()));

        // Interest rate column
        interestRateColumn.setCellValueFactory(cell -> {
            Account account = cell.getValue();
            if (account instanceof InterestBearing) {
                InterestBearing interestAccount = (InterestBearing) account;
                double rate = interestAccount.getInterestRate() * 100;
                return new SimpleStringProperty(String.format("%.2f%%", rate));
            }
            return new SimpleStringProperty("N/A");
        });

        // Format balance column as currency
        balanceColumn.setCellFactory(col -> new TableCell<Account, Double>() {
            @Override
            protected void updateItem(Double balance, boolean empty) {
                super.updateItem(balance, empty);
                if (empty || balance == null) {
                    setText(null);
                } else {
                    setText(String.format("BWP %.2f", balance));
                }
            }
        });

        // Add action buttons to each row
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button transactionBtn = new Button("Transactions");

            {
                transactionBtn.setOnAction(event -> {
                    Account account = getTableView().getItems().get(getIndex());
                    openTransactionPage(account);
                });
                transactionBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; " +
                        "-fx-background-radius: 6; -fx-padding: 6 12;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(transactionBtn);
                }
            }
        });

        // Load data
        loadAccounts();
        updateSummary();
    }

    private void loadAccounts() {
        try {
            if (currentCustomer != null) {
                welcomeLabel.setText("Welcome, " + currentCustomer.getName());
                List<Account> accounts = accountDAO.getAccountsByCustomer(currentCustomer);
                ObservableList<Account> list = FXCollections.observableArrayList(accounts);
                accountsTable.setItems(list);
            }
        } catch (Exception e) {
            showAlert("Error", "Could not load accounts: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updateSummary() {
        try {
            if (currentCustomer != null) {
                List<Account> accounts = accountDAO.getAccountsByCustomer(currentCustomer);

                // Calculate total balance
                double totalBalance = accounts.stream()
                        .mapToDouble(Account::getBalance)
                        .sum();

                totalBalanceLabel.setText(String.format("BWP %.2f", totalBalance));
                accountCountLabel.setText(String.valueOf(accounts.size()));

                // Count interest-bearing accounts
                long interestAccounts = accounts.stream()
                        .filter(acc -> acc instanceof InterestBearing)
                        .count();

                interestAccountsLabel.setText(String.valueOf(interestAccounts));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleOpenAccount() {
        try {
            BankingApp.showAccountCreation();
        } catch (IOException e) {
            showAlert("Error", "Could not open account creation page: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleApplyInterest() {
        try {
            if (currentCustomer != null) {
                List<Account> accounts = accountDAO.getAccountsByCustomer(currentCustomer);
                int interestAppliedCount = 0;
                int skippedCount = 0;
                double totalInterestApplied = 0.0;
                StringBuilder details = new StringBuilder();
                StringBuilder skipped = new StringBuilder();

                for (Account account : accounts) {
                    if (account instanceof InterestBearing) {
                        // Check if eligible for interest (30-day rule)
                        if (!account.isEligibleForInterest()) {
                            skippedCount++;
                            LocalDate nextEligibleDate = account.getLastInterestDate().plusDays(30);
                            skipped.append(String.format("⏳ %s (%s): Last applied %s. Next eligible: %s\n",
                                    account.getAccountType(),
                                    account.getAccountNumber(),
                                    account.getLastInterestDate(),
                                    nextEligibleDate));
                            continue; // Skip this account
                        }

                        InterestBearing interestAccount = (InterestBearing) account;
                        double balanceBefore = account.getBalance();
                        double interestAmount = interestAccount.calculateInterest();

                        // Apply interest
                        interestAccount.applyInterest();

                        // Update last interest date
                        account.setLastInterestDate(LocalDate.now());

                        // Get the interest transaction that was just created
                        List<Transaction> transactions = account.getTransactions();
                        if (!transactions.isEmpty()) {
                            Transaction lastTx = transactions.get(transactions.size() - 1);
                            if (lastTx.getType() == Transaction.TransactionType.INTEREST) {
                                transactionDAO.addTransaction(lastTx);
                            }
                        }

                        // Update account balance AND last interest date in database
                        accountDAO.updateAccount(account);

                        interestAppliedCount++;
                        totalInterestApplied += interestAmount;

                        details.append(String.format("✅ %s (%s): BWP %.2f → BWP %.2f (+BWP %.2f)\n",
                                account.getAccountType(),
                                account.getAccountNumber(),
                                balanceBefore,
                                account.getBalance(),
                                interestAmount));
                    }
                }

                // Build result message
                StringBuilder message = new StringBuilder();

                if (interestAppliedCount > 0) {
                    message.append(String.format(
                            "Interest applied to %d account(s)\n" +
                                    "Total interest earned: BWP %.2f\n\n" +
                                    "Details:\n%s",
                            interestAppliedCount,
                            totalInterestApplied,
                            details.toString()
                    ));
                }

                if (skippedCount > 0) {
                    if (interestAppliedCount > 0) {
                        message.append("\n");
                    }
                    message.append(String.format(
                            "\n⚠️ %d account(s) skipped (not yet eligible):\n%s\n" +
                                    "💡 Note: Interest can only be applied once every 30 days per account.",
                            skippedCount,
                            skipped.toString()
                    ));
                }

                if (interestAppliedCount == 0 && skippedCount == 0) {
                    message.append(
                            "No interest-bearing accounts found.\n\n" +
                                    "Interest is only applied to:\n" +
                                    "• Savings Accounts (0.05% monthly)\n" +
                                    "• Investment Accounts (5% monthly)"
                    );
                }

                Alert alert = new Alert(
                        interestAppliedCount > 0 ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING
                );
                alert.setTitle("Monthly Interest");
                alert.setHeaderText(interestAppliedCount > 0 ?
                        "✅ Interest Application Complete" :
                        "⚠️ Interest Application");
                alert.setContentText(message.toString());
                alert.showAndWait();

                // Refresh dashboard if any interest was applied
                if (interestAppliedCount > 0) {
                    loadAccounts();
                    updateSummary();
                }
            }
        } catch (Exception e) {
            showAlert("Error", "Could not apply interest: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void openTransactionPage(Account account) {
        try {
            BankingApp.showTransactions();
        } catch (IOException e) {
            showAlert("Error", "Could not open transaction page", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleLogout() {
        try {
            currentCustomer = null;
            BankingApp.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
