package banking.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class LoginView extends VBox {
    
    // FXML fields match the fx:id attributes in LoginPage.fxml
    @FXML
    private TextField identifierField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Button registerButton;
    
    // --- Getters for Controller (Week 5) to pull user input ---
    
    /**
     * Retrieves the text entered by the user in the identifier field.
     * @return The customer identifier (National ID or Registration Number).
     */
    public String getIdentifierInput() {
        return identifierField.getText();
    }
    
    // --- Getters for Controller (Week 5) to attach event listeners ---
    
    /**
     * Provides the Login button object for the Controller to attach an action listener.
     * @return The Login button.
     */
    public Button getLoginButton() {
        return loginButton;
    }
    
    /**
     * Provides the Register button object for the Controller to attach an action listener.
     * @return The Register button.
     */
    public Button getRegisterButton() {
        return registerButton;
    }
}
