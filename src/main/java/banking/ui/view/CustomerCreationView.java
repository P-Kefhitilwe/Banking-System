package banking.ui.view;

import banking.controller.CustomerController;
import banking.ui.BankingApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;

/**
 * View class for the customer registration screen.
 * Handles the creation of new customers and their initial accounts.
 */
public class CustomerCreationView extends VBox {
    
    private final CustomerController controller;

    // Form Fields
    @FXML private TextField nameField;
    @FXML private TextField identifierField;
    @FXML private Label identifierLabel;
    @FXML private ChoiceBox<String> customerTypeChoiceBox;
    @FXML private ChoiceBox<String> accountTypeChoiceBox;
    @FXML private TextField initialDepositField;
    
    // Action Buttons
    @FXML private Button submitButton;
    @FXML private Button backButton;

    /**
     * Constructor for the CustomerCreationView.
     * Loads the FXML file and sets up the controller.
     */
    public CustomerCreationView() {
        this.controller = new CustomerController();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banking/ui/CustomerCreation.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
            
            // Initialize UI components
            initialize();
        } catch (IOException e) {
            BankingApp.showError("Failed to load customer creation view", e);
        }
    }
    
    /**
     * Initializes the view components and sets up event handlers.
     */
    private void initialize() {
        // Initialize choice boxes
        initializeChoiceBoxes();
        
        // Set up numeric formatter for initial deposit
        setupNumericFormatter();
        
        // Set up event handlers
        submitButton.setOnAction(event -> handleSubmit());
        backButton.setOnAction(event -> handleBack());
        
        // Update identifier label when customer type changes
        customerTypeChoiceBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateIdentifierLabel();
        });
    }
    
    /**
     * Initializes the choice boxes with their options.
     */
    private void initializeChoiceBoxes() {
        // Customer type options
        customerTypeChoiceBox.getItems().addAll("Individual", "Company");
        customerTypeChoiceBox.setValue("Individual");
        
        // Account type options
        accountTypeChoiceBox.getItems().addAll("Savings", "Investment", "Cheque");
        accountTypeChoiceBox.setValue("Savings");
    }
    
    /**
     * Sets up a numeric formatter for the initial deposit field.
     */
    private void setupNumericFormatter() {
        // Only allow numeric input with optional decimal point
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        };
        
        initialDepositField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    /**
     * Updates the identifier label based on the selected customer type.
     */
    private void updateIdentifierLabel() {
        String customerType = customerTypeChoiceBox.getValue();
        if ("Individual".equals(customerType)) {
            identifierLabel.setText("National ID:");
            identifierField.setPromptText("Enter National ID");
            identifierField.clear();
        } else {
            identifierLabel.setText("Registration No.:");
            identifierField.setPromptText("Enter Registration Number");
            identifierField.clear();
        }
    }
    
    /**
     * Handles the submit button click event.
     */
    private void handleSubmit() {
        // Validate input
        if (!validateInput()) {
            return;
        }
        
        try {
            // Get form values
            String name = nameField.getText().trim();
            String identifier = identifierField.getText().trim();
            String customerType = customerTypeChoiceBox.getValue();
            String accountType = accountTypeChoiceBox.getValue();
            double initialDeposit = Double.parseDouble(initialDepositField.getText());
            
            // Create customer and account
            boolean success = controller.createCustomerAndAccount(
                name, 
                identifier, 
                customerType, 
                accountType, 
                initialDeposit
            );
            
            if (success) {
                showSuccess("Account created successfully!");
                clearForm();
            } else {
                showError("Account Creation Failed", "Failed to create account. Please try again.");
            }
        } catch (Exception e) {
            BankingApp.showError("Error creating account", e);
        }
    }
    
    /**
     * Validates the form input.
     * @return true if input is valid, false otherwise
     */
    private boolean validateInput() {
        // Check name
        if (nameField.getText().trim().isEmpty()) {
            showError("Validation Error", "Please enter a name.");
            return false;
        }
        
        // Check identifier
        if (identifierField.getText().trim().isEmpty()) {
            String fieldName = customerTypeChoiceBox.getValue().equals("Individual") 
                ? "National ID" 
                : "Registration Number";
            showError("Validation Error", "Please enter a " + fieldName + ".");
            return false;
        }
        
        // Check initial deposit
        try {
            double deposit = Double.parseDouble(initialDepositField.getText());
            if (deposit < 0) {
                showError("Validation Error", "Initial deposit cannot be negative.");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Validation Error", "Please enter a valid initial deposit amount.");
            return false;
        }
        
        return true;
    }
    
    /**
     * Handles the back button click event.
     */
    private void handleBack() {
        try {
            controller.navigateBack();
        } catch (Exception e) {
            BankingApp.showError("Failed to navigate back", e);
        }
    }
    
    /**
     * Clears the form fields.
     */
    public void clearForm() {
        nameField.clear();
        identifierField.clear();
        initialDepositField.clear();
        customerTypeChoiceBox.setValue("Individual");
        accountTypeChoiceBox.setValue("Savings");
    }
    
    /**
     * Shows a success message.
     * @param message The message to display
     */
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Shows an error message.
     * @param title The title of the error dialog
     * @param message The error message to display
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
