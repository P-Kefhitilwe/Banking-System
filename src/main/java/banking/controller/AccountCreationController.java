package banking.controller;

import banking.BankingApp;
import banking.model.*;
import banking.util.dao.AccountDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Random;

public class AccountCreationController {
    @FXML private ComboBox<String> accountTypeCombo;
    @FXML private TextField initialDepositField;
    @FXML private TextField branchField;
    @FXML private VBox chequeFieldsBox;
    @FXML private TextField employerNameField;
    @FXML private TextField employerAddressField;
    @FXML private Label messageLabel;

    private final AccountDAO accountDAO = new AccountDAO();

    @FXML
    public void initialize() {
        // Populate account type combo box
        accountTypeCombo.setItems(FXCollections.observableArrayList(
                "Savings Account",
                "Investment Account",
                "Cheque Account"
        ));

        // Listen for account type changes
        accountTypeCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if ("Cheque Account".equals(newVal)) {
                chequeFieldsBox.setVisible(true);
                chequeFieldsBox.setManaged(true);
            } else {
                chequeFieldsBox.setVisible(false);
                chequeFieldsBox.setManaged(false);
            }
        });
    }

    @FXML
    public void handleCreateAccount() {
        try {
            // Validate inputs
            String accountType = accountTypeCombo.getValue();
            if (accountType == null) {
                showError("Please select an account type.");
                return;
            }

            String depositText = initialDepositField.getText().trim();
            if (depositText.isEmpty()) {
                showError("Please enter initial deposit amount.");
                return;
            }

            double initialDeposit;
            try {
                initialDeposit = Double.parseDouble(depositText);
                if (initialDeposit <= 0) {
                    showError("Initial deposit must be positive.");
                    return;
                }
            } catch (NumberFormatException e) {
                showError("Invalid amount format.");
                return;
            }

            String branch = branchField.getText().trim();
            if (branch.isEmpty()) {
                showError("Please enter branch name.");
                return;
            }

            // Get current customer
            Customer customer = DashboardController.currentCustomer;
            if (customer == null) {
                showError("No customer logged in.");
                return;
            }

            // Generate unique account number
            String accountNumber = generateAccountNumber();

            // Create account based on type
            Account newAccount;
            switch (accountType) {
                case "Savings Account":
                    newAccount = new SavingsAccount(accountNumber, initialDeposit, customer, branch);
                    break;

                case "Investment Account":
                    if (initialDeposit < InvestmentAccount.getMinimumOpeningBalance()) {
                        showError("Investment account requires minimum BWP " +
                                InvestmentAccount.getMinimumOpeningBalance());
                        return;
                    }
                    newAccount = new InvestmentAccount(accountNumber, initialDeposit, customer, branch);
                    break;

                case "Cheque Account":
                    String employerName = employerNameField.getText().trim();
                    String employerAddress = employerAddressField.getText().trim();

                    if (employerName.isEmpty() || employerAddress.isEmpty()) {
                        showError("Please provide employer details for Cheque account.");
                        return;
                    }

                    newAccount = new ChequeAccount(accountNumber, initialDeposit, customer,
                            branch, employerName, employerAddress);
                    break;

                default:
                    showError("Unknown account type.");
                    return;
            }

            // Save to database
            accountDAO.addAccount(newAccount);

            showSuccess("✅ Account created successfully!\nAccount Number: " + accountNumber);

            // Clear form after 2 seconds and go back to dashboard
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    javafx.application.Platform.runLater(() -> {
                        try {
                            BankingApp.showDashboard();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            showError("Error creating account: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancel() {
        try {
            BankingApp.showDashboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateAccountNumber() {
        Random random = new Random();
        return String.format("ACC%010d", random.nextInt(1000000000));
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
