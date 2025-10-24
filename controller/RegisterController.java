package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import service.UserService;
import model.User;

public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField incomeField;
    @FXML private TextField taxField;
    @FXML private TextField idField; // National ID / registration
    @FXML private ChoiceBox<String> customerTypeChoice;

    private UserService userService = service.AppContext.getInstance().getUserService();

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();

        if (username == null || username.isEmpty() || password == null || password.isEmpty() || name == null || name.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Username, password and name are required").showAndWait();
            return;
        }

        User user = new User(username, password, name);
        boolean registered = userService.register(user);
        if (registered) {
            new Alert(Alert.AlertType.INFORMATION, "Registration successful! You can now log in.").showAndWait();
            goBackToLogin(event);
        } else {
            new Alert(Alert.AlertType.ERROR, "Registration failed. User may already exist.").showAndWait();
        }
    }

    @FXML
    private void goBackToLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScreen.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot go back to login").showAndWait();
        }
    }
}
