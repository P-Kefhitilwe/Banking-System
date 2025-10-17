package banking.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main JavaFX application launcher. This class initializes the primary stage
 * and loads the initial view (LoginPage.fxml).
 * This class belongs to the Boundary Layer.
 */
public class BankingApp extends Application {

    private static final int INITIAL_WIDTH = 400;
    private static final int INITIAL_HEIGHT = 450; // Increased height for better fit

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the initial scene defined in LoginPage.fxml
            // The resource path is relative to the resources folder (src/main/resources)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banking/ui/LoginPage.fxml"));
            VBox root = loader.load();

            Scene scene = new Scene(root, INITIAL_WIDTH, INITIAL_HEIGHT);
            
            // Set up the stage (window)
            primaryStage.setTitle("Banking System - Customer Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Often good practice for simple forms
            primaryStage.show();
            
            // Note: The main logic (Controller) will be handled in Week 5,
            // where the controller will be injected and initialized.
        } catch (IOException e) {
            System.err.println("Error loading FXML file: /banking/ui/LoginPage.fxml");
            e.printStackTrace();
        }
    }

    /**
     * Standard main method to launch the JavaFX application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
