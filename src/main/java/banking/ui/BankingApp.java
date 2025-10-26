package banking.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The main JavaFX application launcher. This class initializes the primary stage
 * and loads the initial view (LoginPage.fxml).
 */
public class BankingApp extends Application {
    
    public static final String APP_TITLE = "Banking Application";
    private static final int INITIAL_WIDTH = 1000;
    private static final int INITIAL_HEIGHT = 700;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        showLoginView();
    }
    
    /**
     * Shows the login view.
     */
    public static void showLoginView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                BankingApp.class.getResource("/banking/ui/LoginPage.fxml")
            );
            Parent root = loader.load();
            
            Scene scene = new Scene(root, INITIAL_WIDTH, INITIAL_HEIGHT);
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            showError("Error loading login view", e);
        }
    }
    
    /**
     * Shows an error dialog with the specified message and exception details.
     * 
     * @param message The error message to display
     * @param e The exception that caused the error (can be null)
     */
    public static void showError(String message, Exception e) {
        System.err.println("ERROR: " + message);
        if (e != null) {
            e.printStackTrace();
        }
        
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Returns the primary stage.
     * 
     * @return The primary stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Main method to launch the application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            showError("A critical error occurred", e);
        }
    }
}
