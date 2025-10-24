package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.User;
import service.AccountService;

public class DashboardController {
    @FXML private Label welcomeLabel;
    @FXML private StackPane mainContent;

    private User loggedInUser;
    private AccountService accountService = service.AppContext.getInstance().getAccountService();

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
        if (welcomeLabel != null && user != null) welcomeLabel.setText("Welcome, " + user.getName());
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
