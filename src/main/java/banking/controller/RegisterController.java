package banking.controller;

import banking.BankingApp;
import banking.model.Customer;
import banking.util.dao.CustomerDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {
    @FXML private TextField nameField;
    @FXML private TextField idNumberField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;

    private final CustomerDAO customerDAO = new CustomerDAO();

    @FXML
    public void handleRegister() {
        messageLabel.setText("");

        // Validate inputs
        String name = nameField.getText().trim();
        String idNumber = idNumberField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (name.isEmpty() || idNumber.isEmpty() || email.isEmpty() ||
                phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        if (password.length() < 6) {
            showError("Password must be at least 6 characters.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match.");
            return;
        }

        try {
            // Check if email already exists
            Customer existing = customerDAO.getCustomerByEmail(email);
            if (existing != null) {
                showError("Email already registered.");
                return;
            }

            // Create new customer
            Customer newCustomer = new Customer(name, idNumber, email, phone, address, password);
            customerDAO.addCustomer(newCustomer);

            showSuccess("Account created successfully! Please login.");

            // Wait 2 seconds then go back to login
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    javafx.application.Platform.runLater(() -> {
                        try {
                            BankingApp.showLoginPage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            showError("Registration error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBackToLogin() {
        try {
            BankingApp.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: 500; -fx-font-size: 13px;");
    }

    private void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #10b981; -fx-font-weight: 500; -fx-font-size: 13px;");
    }
}
