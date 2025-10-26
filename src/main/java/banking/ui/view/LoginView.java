package banking.ui.view;

import banking.controller.LoginController;
import banking.ui.BankingApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.IOException;

/**
 * View class for the login screen.
 * Handles user interaction and delegates business logic to the controller.
 */
public class LoginView extends VBox {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label errorLabel;
    
    private final LoginController controller;
    
    /**
     * Constructor for the LoginView.
     * Loads the FXML file and sets up the controller.
     */
    public LoginView() {
        this.controller = new LoginController();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banking/ui/LoginPage.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
            
            // Initialize UI components
            initialize();
        } catch (IOException e) {
            BankingApp.showError("Failed to load login view", e);
        }
    }
    
    /**
     * Initializes the view components and sets up event handlers.
     */
    private void initialize() {
        // Clear error message when user starts typing
        usernameField.textProperty().addListener((obs, oldVal, newVal) -> clearError());
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> clearError());
        
        // Set up event handlers
        loginButton.setOnAction(event -> handleLogin());
        registerButton.setOnAction(event -> handleRegister());
        
        // Allow pressing Enter to log in
        passwordField.setOnAction(event -> handleLogin());
    }
    
    /**
     * Handles the login button click event.
     */
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password");
            return;
        }
        
        try {
            boolean success = controller.authenticate(username, password);
            if (success) {
                // Clear sensitive data
                passwordField.clear();
                clearError();
                
                // Navigate to the main application
                controller.navigateToDashboard();
            } else {
                showError("Invalid username or password");
                passwordField.clear();
            }
        } catch (Exception e) {
            BankingApp.showError("Login failed", e);
        }
    }
    
    /**
     * Handles the register button click event.
     */
    private void handleRegister() {
        try {
            controller.navigateToRegistration();
        } catch (Exception e) {
            BankingApp.showError("Failed to navigate to registration", e);
        }
    }
    
    /**
     * Displays an error message to the user.
     * @param message The error message to display
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
    
    /**
     * Clears any displayed error message.
     */
    private void clearError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }
    
    /**
     * Clears the form fields.
     */
    public void clearForm() {
        usernameField.clear();
        passwordField.clear();
        clearError();
    }
    
    /**
     * Provides the Register button object for the Controller to attach an action listener.
     * @return The Register button.
     */
    /**
     * Provides the Register button object for the Controller to attach an action listener.
     * @return The Register button.
     */
    public Button getRegisterButton() {
        return registerButton;
    }
}
