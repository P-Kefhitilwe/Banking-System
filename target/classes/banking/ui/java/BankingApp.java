/*
 * Reference copy of BankingApp.java (moved to resources to avoid compile-time JavaFX dependency).
 * To enable the UI, move this file back to src/main/java/banking/ui and add OpenJFX dependencies.
 */
package banking.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class BankingApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banking/ui/LoginPage.fxml"));
        VBox root = loader.load();
        Scene scene = new Scene(root, 400, 450);
        primaryStage.setTitle("Banking System - Customer Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
