package banking.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Boundary Class for Transaction Accounts (Investment/Cheque).
 * Exposes UI elements for the Controller to manage deposits and withdrawals.
 */
public class TransactionAccountView extends VBox {

    // Account Display
    @FXML private Label accountTypeLabel;
    @FXML private Label accountNumberLabel;
    @FXML private Label currentBalanceLabel;

    // Transaction Inputs
    @FXML private TextField depositAmountField;
    @FXML private Button depositButton;
    @FXML private TextField withdrawAmountField;
    @FXML private Button withdrawButton;
    
    // History and Navigation
    @FXML private TextArea historyArea;
    @FXML private Button backToSelectionButton;

    // --- Setters for Controller (To update the display) ---

    /**
     * Updates the account header details.
     * @param type The account type (e.g., "Investment" or "Cheque").
     * @param number The account number.
     * @param balance The current balance.
     */
    public void setAccountDetails(String type, String number, double balance) {
        accountTypeLabel.setText(type + " Account Details");
        accountNumberLabel.setText(number);
        currentBalanceLabel.setText(String.format("BWP %.2f", balance));
    }
    
    /**
     * Appends a new transaction entry to the history text area.
     * @param transaction The transaction string to display.
     */
    public void appendToHistory(String transaction) {
        historyArea.appendText(transaction + "\n");
    }

    // --- Getters for Controller (To retrieve user input and attach handlers) ---

    public String getDepositAmountText() { return depositAmountField.getText(); }
    public String getWithdrawAmountText() { return withdrawAmountField.getText(); }
    
    public Button getDepositButton() { return depositButton; }
    public Button getWithdrawButton() { return withdrawButton; }
    public Button getBackToSelectionButton() { return backToSelectionButton; }
}
