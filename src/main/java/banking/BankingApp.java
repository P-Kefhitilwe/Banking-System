package banking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BankingApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("Banking System - OOP Assignment");

        // Load the login page
        showLoginPage();
    }

    public static void showLoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankingApp.class.getResource("/fxml/LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankingApp.class.getResource("/fxml/Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showAccountCreation() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankingApp.class.getResource("/fxml/AccountCreation.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showTransactions() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankingApp.class.getResource("/fxml/Transaction.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}
