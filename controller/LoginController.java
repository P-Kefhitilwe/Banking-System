package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import service.AuthService;
import model.User;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private AuthService authService = service.AppContext.getInstance().getAuthService();

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean success = authService.login(username, password);
        if (success) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
                Parent root = loader.load();
                // Optionally pass user to dashboard controller
                DashboardController dc = loader.getController();
                // fetch full User from AuthService so dashboard has real user data
                dc.setLoggedInUser(service.AppContext.getInstance().getAuthService().getUserByUsername(username));

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.getScene().setRoot(root);
            } catch (Exception e) {
                e.printStackTrace();
                messageLabel.setText("Failed to load dashboard.");
            }
        } else {
            messageLabel.setText("Invalid credentials. Try again.");
        }
    }

    @FXML
    private void showRegisterScreen(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/RegisterScreen.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot open register screen").showAndWait();
        }
    }
}
