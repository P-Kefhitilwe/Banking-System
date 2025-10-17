package banking.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Boundary Class for Customer Registration.
 * Exposes UI elements for the Controller to manage the creation of
 * IndividualCustomer or CompanyCustomer and their initial account.
 */
public class CustomerCreationView extends VBox {

    // Customer Detail Fields
    @FXML private TextField nameField; 
    @FXML private TextField identifierField; 
    @FXML private Label identifierLabel; 
    
    // Type Selectors
    @FXML private ChoiceBox<String> typeChoiceBox;
    @FXML private ChoiceBox<String> accountTypeChoiceBox;
    
    // Initial Deposit and Actions
    @FXML private TextField initialDepositField;
    @FXML private Button registerAndOpenButton;
    @FXML private Button backToLoginButton;

    /**
     * Initialization method run after FXML is loaded.
     * Sets up default values for ChoiceBoxes and links listener for ID label change.
     */
    @FXML
    public void initialize() {
        // Customer Type
        typeChoiceBox.getItems().addAll("Individual", "Company");
        typeChoiceBox.setValue("Individual"); 
        
        // Account Type
        accountTypeChoiceBox.getItems().addAll("Savings", "Investment", "Cheque");
        accountTypeChoiceBox.setValue("Savings"); 
        
        // Listener to change the ID field label based on Customer Type selected
        typeChoiceBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if ("Individual".equals(newValue)) {
                identifierLabel.setText("National ID:");
                identifierField.setPromptText("Enter National ID");
            } else if ("Company".equals(newValue)) {
                identifierLabel.setText("Registration No.:");
                identifierField.setPromptText("Enter Registration Number");
            }
        });
    }

    // --- Getters for Controller (To retrieve user input and attach handlers) ---

    public String getCustomerName() { return nameField.getText(); }
    public String getIdentifier() { return identifierField.getText(); }
    public String getCustomerType() { return typeChoiceBox.getValue(); }
    public String getAccountType() { return accountTypeChoiceBox.getValue(); }
    public String getInitialDepositText() { return initialDepositField.getText(); }
    
    // Action Buttons
    public Button getRegisterButton() { return registerAndOpenButton; }
    public Button getBackToLoginButton() { return backToLoginButton; }
}
