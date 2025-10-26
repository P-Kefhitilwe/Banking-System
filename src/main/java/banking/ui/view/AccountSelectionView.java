package banking.ui.view;

import banking.model.account.Account;
import banking.ui.BankingApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * View class for the account selection screen.
 * Displays a list of accounts belonging to the logged-in customer
 * and provides navigation/management buttons.
 */
public class AccountSelectionView extends VBox {
    
    private final AccountController controller;

    // Display Fields
    @FXML private Label customerNameLabel;
    @FXML private Label customerIdentifierLabel;

    // Account List
    @FXML private ListView<String> accountListView;
    
    // Action Buttons
    @FXML private Button manageAccountButton;
    @FXML private Button createAccountButton;
    @FXML private Button backButton;
    @FXML private Button processInterestButton;
    @FXML private Button logoutButton;

    /**
     * Constructor for the AccountSelectionView.
     * Loads the FXML file and sets up the controller.
     */
    public AccountSelectionView() {
        this.controller = new AccountController();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banking/ui/AccountSelection.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
            
            // Initialize UI components
            initialize();
        } catch (IOException e) {
            BankingApp.showError("Failed to load account selection view", e);
        }
    }
    
    /**
     * Initializes the view components and sets up event handlers.
     */
    private void initialize() {
        // Set up event handlers
        manageAccountButton.setOnAction(event -> handleManageAccount());
        createAccountButton.setOnAction(event -> handleCreateAccount());
        backButton.setOnAction(event -> handleBack());
        processInterestButton.setOnAction(event -> handleProcessInterest());
        logoutButton.setOnAction(event -> handleLogout());
        
        // Disable manage button when no account is selected
        accountListView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> manageAccountButton.setDisable(newVal == null)
        );
    }
    
    /**
     * Handles the Manage Account button click event.
     */
    private void handleManageAccount() {
        String selectedAccount = getSelectedAccount();
        if (selectedAccount != null) {
            try {
                // Extract account number from the selected item (assuming format: "ACC-123 (Type) - Balance")
                String accountNumber = selectedAccount.split("\\s+")[0];
                controller.navigateToAccount(accountNumber);
            } catch (Exception e) {
                BankingApp.showError("Failed to open account", e);
            }
        }
    }
    
    /**
     * Handles the Create Account button click event.
     */
    private void handleCreateAccount() {
        try {
            controller.navigateToAccountCreation();
        } catch (Exception e) {
            BankingApp.showError("Failed to navigate to account creation", e);
        }
    }
    
    /**
     * Handles the Back button click event.
     */
    private void handleBack() {
        try {
            controller.navigateBack();
        } catch (Exception e) {
            BankingApp.showError("Failed to navigate back", e);
        }
    }
    
    /**
     * Handles the Process Interest button click event.
     */
    private void handleProcessInterest() {
        try {
            if (controller.processInterestForAllAccounts()) {
                showInfo("Interest processed successfully", "Interest has been applied to all applicable accounts.");
                refreshAccountList();
            } else {
                showError("Failed to process interest", "Please try again later.");
            }
        } catch (Exception e) {
            BankingApp.showError("Error processing interest", e);
        }
    }
    
    /**
     * Handles the Logout button click event.
     */
    private void handleLogout() {
        try {
            controller.logout();
        } catch (Exception e) {
            BankingApp.showError("Error during logout", e);
        }
    }
    
    /**
     * Refreshes the account list from the controller.
     */
    public void refreshAccountList() {
        String[] accounts = controller.getCustomerAccounts();
        if (accounts != null) {
            setAccountList(accounts);
        }
    }
    
    /**
     * Sets the customer details in the view.
     * @param name The customer's name
     * @param identifier The customer's ID or registration number
     */
    public void setCustomerDetails(String name, String identifier) {
        customerNameLabel.setText(name);
        customerIdentifierLabel.setText("ID: " + identifier);
        
        // Refresh the account list after setting customer details
        refreshAccountList();
    }
    
    /**
     * Updates the list of accounts displayed to the customer.
     * @param accounts An array of strings representing the accounts.
     */
    public void setAccountList(String[] accounts) {
        accountListView.getItems().clear();
        if (accounts != null && accounts.length > 0) {
            accountListView.getItems().addAll(accounts);
            // Select the first account by default
            accountListView.getSelectionModel().selectFirst();
        }
    }
    
    /**
     * Gets the account string currently selected by the user in the list.
     * @return The selected account string, or null if none is selected.
     */
    public String getSelectedAccount() {
        return accountListView.getSelectionModel().getSelectedItem();
    }
    
    /**
     * Shows an information alert.
     * @param title The title of the alert
     * @param message The message to display
     */
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Shows an error alert.
     * @param title The title of the alert
     * @param message The message to display
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Getters for controller to attach additional listeners if needed
    public Button getManageAccountButton() { return manageAccountButton; }
    public Button getProcessInterestButton() { return processInterestButton; }
    public Button getLogoutButton() { return logoutButton; }
    public Button getCreateAccountButton() { return createAccountButton; }
    public Button getBackButton() { return backButton; }
}
