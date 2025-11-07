package banking.controller;

import banking.BankingApp;
import banking.model.Customer;
import banking.util.dao.CustomerDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final CustomerDAO customerDAO = new CustomerDAO();

    @FXML
    public void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        errorLabel.setText("");

        if (email.isEmpty() || password.isEmpty()) {
            showError("Please enter both email and password.");
            return;
        }

        try {
            Customer customer = customerDAO.getCustomerByEmail(email);
            if (customer != null && customer.getPassword().equals(password)) {
                DashboardController.currentCustomer = customer;
                BankingApp.showDashboard();
            } else {
                showError("Invalid email or password.");
            }
        } catch (Exception e) {
            showError("Login error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Register.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);
            Stage stage = BankingApp.getPrimaryStage();
            stage.setScene(scene);
        } catch (Exception e) {
            showError("Error opening registration page: " + e.getMessage());
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: 500;");
    }
}
