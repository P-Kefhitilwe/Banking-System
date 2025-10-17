// BankingApp.java moved to resources to avoid JavaFX compile-time dependency when JavaFX jars are not available.
// The original JavaFX source is preserved here for reference.
// To enable the JavaFX UI, move this file back to src/main/java/banking/ui and add JavaFX dependencies to the build.
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
