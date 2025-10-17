package banking.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * Boundary Class for Account Selection.
 * Displays a list of accounts belonging to the logged-in customer
 * and provides navigation/management buttons.
 */
public class AccountSelectionView extends VBox {

    // Display Fields
    @FXML private Label customerNameLabel;
    @FXML private Label customerIdentifierLabel;

    // Account List
    @FXML private ListView<String> accountListView;
    
    // Action Buttons
    @FXML private Button manageAccountButton;
    @FXML private Button processInterestButton;
    @FXML private Button logoutButton;

    // --- Setters for Controller (To update the display) ---

    /**
     * Sets the display name and identifier for the currently logged-in customer.
     * @param name The customer's full name or company name.
     * @param identifier The customer's National ID or Registration Number.
     */
    public void setCustomerDetails(String name, String identifier) {
        customerNameLabel.setText(name);
        customerIdentifierLabel.setText("ID: " + identifier);
    }
    
    /**
     * Updates the list of accounts displayed to the customer.
     * @param accounts A list of strings representing the accounts (e.g., "SAV-001 (Savings) - BWP 150.00").
     */
    public void setAccountList(String[] accounts) {
        accountListView.getItems().clear();
        accountListView.getItems().addAll(accounts);
    }

    // --- Getters for Controller (To retrieve user input and attach handlers) ---

    /**
     * Gets the account string currently selected by the user in the list.
     * @return The selected account string, or null if none is selected.
     */
    public String getSelectedAccount() {
        return accountListView.getSelectionModel().getSelectedItem();
    }

    public Button getManageAccountButton() { return manageAccountButton; }
    public Button getProcessInterestButton() { return processInterestButton; }
    public Button getLogoutButton() { return logoutButton; }
}
