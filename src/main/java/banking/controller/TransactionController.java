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
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionController {
    @FXML private ComboBox<Account> accountCombo;
    @FXML private ComboBox<String> transactionTypeCombo;
    @FXML private VBox targetAccountBox;
    @FXML private ComboBox<Account> targetAccountCombo;
    @FXML private TextField amountField;
    @FXML private Label balanceLabel;
    @FXML private Label accountInfoLabel;
    @FXML private Label messageLabel;
    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, Double> amountColumn;
    @FXML private TableColumn<Transaction, Double> balanceAfterColumn;
    @FXML private TableColumn<Transaction, String> descriptionColumn;

    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    @FXML
    public void initialize() {
        // Populate transaction type combo box with Transfer option
        transactionTypeCombo.setItems(FXCollections.observableArrayList(
                "Deposit",
                "Withdraw",
                "Transfer"
        ));

        // Setup table columns
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dateColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getTimestamp().format(formatter)));
        typeColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getType().toString()));
        amountColumn.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getAmount()));
        balanceAfterColumn.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getBalanceAfter()));
        descriptionColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDescription() != null ?
                        cell.getValue().getDescription() : ""));

        // Format currency columns
        amountColumn.setCellFactory(col -> new TableCell<Transaction, Double>() {
            @Override
            protected void updateItem(Double amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                } else {
                    setText(String.format("BWP %.2f", amount));
                }
            }
        });

        balanceAfterColumn.setCellFactory(col -> new TableCell<Transaction, Double>() {
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

        // Load customer accounts
        loadAccounts();

        // Listen for account selection changes
        accountCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateAccountInfo(newVal);
                loadTransactions(newVal);
                // Update target account options if transfer is selected
                if ("Transfer".equals(transactionTypeCombo.getValue())) {
                    loadTargetAccounts();
                }
            }
        });

        // Listen for transaction type changes
        transactionTypeCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean isTransfer = "Transfer".equals(newVal);
            targetAccountBox.setVisible(isTransfer);
            targetAccountBox.setManaged(isTransfer);

            if (isTransfer) {
                loadTargetAccounts();
            }
        });

        // Custom display for accounts in combo boxes
        setupAccountComboDisplay(accountCombo);
        setupAccountComboDisplay(targetAccountCombo);
    }

    private void setupAccountComboDisplay(ComboBox<Account> combo) {
        combo.setCellFactory(param -> new ListCell<Account>() {
            @Override
            protected void updateItem(Account account, boolean empty) {
                super.updateItem(account, empty);
                if (empty || account == null) {
                    setText(null);
                } else {
                    setText(String.format("%s - %s (BWP %.2f)",
                            account.getAccountType(),
                            account.getAccountNumber(),
                            account.getBalance()));
                }
            }
        });

        combo.setButtonCell(new ListCell<Account>() {
            @Override
            protected void updateItem(Account account, boolean empty) {
                super.updateItem(account, empty);
                if (empty || account == null) {
                    setText(null);
                } else {
                    setText(String.format("%s - %s",
                            account.getAccountType(),
                            account.getAccountNumber()));
                }
            }
        });
    }

    private void loadAccounts() {
        try {
            Customer customer = DashboardController.currentCustomer;
            if (customer != null) {
                List<Account> accounts = accountDAO.getAccountsByCustomer(customer);
                accountCombo.setItems(FXCollections.observableArrayList(accounts));
                if (!accounts.isEmpty()) {
                    accountCombo.setValue(accounts.get(0));
                }
            }
        } catch (Exception e) {
            showError("Error loading accounts: " + e.getMessage());
        }
    }

    private void loadTargetAccounts() {
        try {
            Customer customer = DashboardController.currentCustomer;
            Account fromAccount = accountCombo.getValue();

            if (customer != null && fromAccount != null) {
                List<Account> allAccounts = accountDAO.getAccountsByCustomer(customer);
                // Exclude the source account from target options
                List<Account> targetAccounts = allAccounts.stream()
                        .filter(acc -> !acc.getAccountNumber().equals(fromAccount.getAccountNumber()))
                        .collect(Collectors.toList());

                targetAccountCombo.setItems(FXCollections.observableArrayList(targetAccounts));
                if (!targetAccounts.isEmpty()) {
                    targetAccountCombo.setValue(targetAccounts.get(0));
                }
            }
        } catch (Exception e) {
            showError("Error loading target accounts: " + e.getMessage());
        }
    }

    private void updateAccountInfo(Account account) {
        balanceLabel.setText(String.format("BWP %.2f", account.getBalance()));
        accountInfoLabel.setText(String.format("%s Account\n%s\nBranch: %s",
                account.getAccountType(),
                account.getAccountNumber(),
                account.getBranch()));
    }

    private void loadTransactions(Account account) {
        try {
            List<Transaction> transactions = transactionDAO.getTransactionsForAccount(
                    account.getAccountNumber());
            ObservableList<Transaction> list = FXCollections.observableArrayList(transactions);
            transactionsTable.setItems(list);
        } catch (Exception e) {
            showError("Error loading transactions: " + e.getMessage());
        }
    }

    @FXML
    public void handleTransaction() {
        try {
            // Validate inputs
            Account selectedAccount = accountCombo.getValue();
            if (selectedAccount == null) {
                showError("Please select an account.");
                return;
            }

            String transactionType = transactionTypeCombo.getValue();
            if (transactionType == null) {
                showError("Please select transaction type.");
                return;
            }

            String amountText = amountField.getText().trim();
            if (amountText.isEmpty()) {
                showError("Please enter amount.");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    showError("Amount must be positive.");
                    return;
                }
            } catch (NumberFormatException e) {
                showError("Invalid amount format.");
                return;
            }

            // Execute transaction based on type
            boolean success = false;
            String message = "";

            if ("Deposit".equals(transactionType)) {
                selectedAccount.deposit(amount);
                success = true;
                message = String.format("✅ Successfully deposited BWP %.2f", amount);

                // Save transaction
                List<Transaction> accountTransactions = selectedAccount.getTransactions();
                if (!accountTransactions.isEmpty()) {
                    Transaction lastTransaction = accountTransactions.get(accountTransactions.size() - 1);
                    transactionDAO.addTransaction(lastTransaction);
                }
                accountDAO.updateAccount(selectedAccount);

            } else if ("Withdraw".equals(transactionType)) {
                if (!(selectedAccount instanceof Withdrawable)) {
                    showError("❌ This account type does not allow withdrawals.");
                    return;
                }

                Withdrawable withdrawableAccount = (Withdrawable) selectedAccount;
                success = withdrawableAccount.withdraw(amount);

                if (success) {
                    message = String.format("✅ Successfully withdrew BWP %.2f", amount);

                    // Save transaction
                    List<Transaction> accountTransactions = selectedAccount.getTransactions();
                    if (!accountTransactions.isEmpty()) {
                        Transaction lastTransaction = accountTransactions.get(accountTransactions.size() - 1);
                        transactionDAO.addTransaction(lastTransaction);
                    }
                    accountDAO.updateAccount(selectedAccount);
                } else {
                    showError("❌ Insufficient funds for withdrawal.");
                    return;
                }

            } else if ("Transfer".equals(transactionType)) {
                Account targetAccount = targetAccountCombo.getValue();
                if (targetAccount == null) {
                    showError("Please select a target account.");
                    return;
                }

                // Check if source account supports withdrawal
                if (!(selectedAccount instanceof Withdrawable)) {
                    showError("❌ Source account does not allow withdrawals.");
                    return;
                }

                Withdrawable withdrawableAccount = (Withdrawable) selectedAccount;
                success = withdrawableAccount.withdraw(amount);

                if (!success) {
                    showError("❌ Insufficient funds for transfer.");
                    return;
                }

                // Deposit to target account
                targetAccount.deposit(amount);

                // Create transfer transactions
                Transaction transferOut = new Transaction(
                        selectedAccount.getAccountNumber(),
                        Transaction.TransactionType.TRANSFER_OUT,
                        amount,
                        selectedAccount.getBalance(),
                        String.format("Transfer to %s", targetAccount.getAccountNumber())
                );
                transactionDAO.addTransaction(transferOut);

                Transaction transferIn = new Transaction(
                        targetAccount.getAccountNumber(),
                        Transaction.TransactionType.TRANSFER_IN,
                        amount,
                        targetAccount.getBalance(),
                        String.format("Transfer from %s", selectedAccount.getAccountNumber())
                );
                transactionDAO.addTransaction(transferIn);

                // Update both accounts in database
                accountDAO.updateAccount(selectedAccount);
                accountDAO.updateAccount(targetAccount);

                message = String.format("✅ Successfully transferred BWP %.2f to %s (%s)",
                        amount, targetAccount.getAccountType(), targetAccount.getAccountNumber());
                success = true;
            }

            if (success) {
                showSuccess(message);
                updateAccountInfo(selectedAccount);
                loadTransactions(selectedAccount);
                amountField.clear();
            }

        } catch (Exception e) {
            showError("Transaction error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBack() {
        try {
            BankingApp.showDashboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: 500; -fx-font-size: 14px;");
    }

    private void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #10b981; -fx-font-weight: 500; -fx-font-size: 14px;");
    }
}
