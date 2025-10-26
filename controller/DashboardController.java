package banking.controller;

import banking.model.customer.Customer;
import banking.service.AccountService;
import banking.service.AuthService;
import banking.service.AppContext;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class DashboardController {
    @FXML private Label welcomeLabel;
    @FXML private StackPane mainContent;

    private Customer loggedInCustomer;
    private AccountService accountService = AppContext.getInstance().getAccountService();

    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
        if (welcomeLabel != null && loggedInCustomer != null) welcomeLabel.setText("Welcome, " + loggedInCustomer.getName());
    }

    @FXML
    private void showAccounts(ActionEvent event) {
        try {
            Parent node = FXMLLoader.load(getClass().getResource("/fxml/AccountScreen.fxml"));
            mainContent.getChildren().setAll(node);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void showTransactions(ActionEvent event) {
        try {
            Parent node = FXMLLoader.load(getClass().getResource("/fxml/TransactionScreen.fxml"));
            mainContent.getChildren().setAll(node);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void showInterestPanel(ActionEvent event) {
        try {
            Parent node = FXMLLoader.load(getClass().getResource("/fxml/InterestScreen.fxml"));
            mainContent.getChildren().setAll(node);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScreen.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
