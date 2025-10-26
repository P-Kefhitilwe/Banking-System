package banking.ui.view;

import banking.controller.AccountController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.IOException;

/**
 * View class for managing individual accounts.
 * Provides functionality for deposits, withdrawals, and viewing transaction history.
 */
public class AccountManagementView extends VBox {
    
    private final AccountController controller;

    // Account Information
    @FXML private Label accountNumberLabel;
    @FXML private Label accountTypeLabel;
    @FXML private Label balanceLabel;
    
    // Transaction Input
    @FXML private TextField amountField;
    @FXML private TextArea descriptionField;
    @FXML private Button depositButton;
    @FXML private Button withdrawButton;
    
    // Transaction History
    @FXML private TableView<?> transactionTable;
    @FXML private TableColumn<?, ?> dateColumn;
    @FXML private TableColumn<?, ?> typeColumn;
    @FXML private TableColumn<?, ?> amountColumn;
    @FXML private TableColumn<?, ?> descriptionColumn;
    @FXML private TableColumn<?, ?> balanceColumn;
    
    // Navigation
    @FXML private Button backButton;
    
    /**
     * Constructor for the AccountManagementView.
     * Loads the FXML file and sets up the controller.
     */
    public AccountManagementView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AccountManagement.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
            
            // Initialize the controller
            this.controller = new AccountController();
            
            // Set up event handlers
            setupEventHandlers();
            
            // Initialize table columns
            initializeTable();
        } catch (IOException e) {
            throw new RuntimeException("Error loading AccountManagement.fxml", e);
        }
    }
    
    /**
     * Initializes the transaction table columns.
     */
    private void initializeTable() {
        // Configure table columns
        dateColumn.setCellValueFactory(cellData -> null); // Replace with actual property
        typeColumn.setCellValueFactory(cellData -> null);
        amountColumn.setCellValueFactory(cellData -> null);
        descriptionColumn.setCellValueFactory(cellData -> null);
        balanceColumn.setCellValueFactory(cellData -> null);
    }
    
    /**
     * Sets up event handlers for the view components.
     */
    private void setupEventHandlers() {
        depositButton.setOnAction(event -> handleDeposit());
        withdrawButton.setOnAction(event -> handleWithdrawal());
        backButton.setOnAction(event -> handleBack());
    }
    
    /**
     * Handles the deposit button click event.
     */
    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();
            
            if (amount <= 0) {
                showError("Amount must be greater than zero");
                return;
            }
            
            boolean success = controller.processDeposit(amount, description);
            if (success) {
                updateAccountInfo();
                clearInputFields();
                showSuccess("Deposit successful!");
            } else {
                showError("Failed to process deposit. Please try again.");
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid amount");
        }
    }
    
    /**
     * Handles the withdrawal button click event.
     */
    private void handleWithdrawal() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();
            
            if (amount <= 0) {
                showError("Amount must be greater than zero");
                return;
            }
            
            boolean success = controller.processWithdrawal(amount, description);
            if (success) {
                updateAccountInfo();
                clearInputFields();
                showSuccess("Withdrawal successful!");
            } else {
                showError("Insufficient funds or withdrawal not allowed for this account type");
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid amount");
        }
    }
    
    /**
     * Handles the back button click event.
     */
    private void handleBack() {
        controller.navigateBack();
    }
    
    /**
     * Updates the account information display.
     * @param accountNumber The account number
     * @param accountType The type of account
     * @param balance The current balance
     */
    public void updateAccountInfo(String accountNumber, String accountType, double balance) {
        accountNumberLabel.setText("Account: " + accountNumber);
        accountTypeLabel.setText("Type: " + accountType);
        balanceLabel.setText(String.format("Balance: BWP %,.2f", balance));
    }
    
    /**
     * Clears the input fields.
     */
    private void clearInputFields() {
        amountField.clear();
        descriptionField.clear();
    }
    
    /**
     * Displays an error message to the user.
     * @param message The error message to display
     */
    private void showError(String message) {
        // In a real application, you would show this in the UI
        System.err.println("Error: " + message);
    }
    
    /**
     * Displays a success message to the user.
     * @param message The success message to display
     */
    private void showSuccess(String message) {
        // In a real application, you would show this in the UI
        System.out.println("Success: " + message);
    }
}
