package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import model.Account;
import model.Transaction;
import service.AccountService;
import service.TransactionService;
import java.util.Date;

public class TransactionController {
    @FXML private ComboBox<Account> accountSelect;
    @FXML private ChoiceBox<String> transactionType;
    @FXML private TextField amountField;
    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, String> transactionIdColumn;
    @FXML private TableColumn<Transaction, Date> dateColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, Double> amountColumn;
    @FXML private TableColumn<Transaction, String> accountColumn;

    private AccountService accountService = service.AppContext.getInstance().getAccountService();
    private TransactionService transactionService = service.AppContext.getInstance().getTransactionService();

    @FXML
    private void initialize() {
        if (accountSelect != null) {
            accountSelect.getItems().setAll(accountService.getAllAccounts());
        }
        if (transactionType != null) {
            transactionType.getItems().addAll("Deposit", "Withdraw");
        }
        if (transactionTable != null) {
            // Configure table columns
            transactionIdColumn.setCellValueFactory(cellData -> cellData.getValue().transactionIdProperty());
            dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
            typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
            amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
            accountColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getAccount().getAccountId()));

            // Load transactions
            transactionTable.getItems().setAll(transactionService.getAll());
        }
    }

    @FXML
    private void handleTransaction(ActionEvent event) {
        Account acc = accountSelect.getValue();
        String type = transactionType.getValue();
        double amount = 0;
        try { amount = Double.parseDouble(amountField.getText()); } catch (Exception e) { new Alert(Alert.AlertType.ERROR, "Invalid amount").showAndWait(); return; }

        if (acc == null || type == null) {
            new Alert(Alert.AlertType.WARNING, "Select account and transaction type").showAndWait();
            return;
        }

        String tid = "T" + (transactionService.getAll().size() + 1);
        transactionService.createTransaction(tid, new Date(), amount, type, acc);
        if (transactionTable != null) transactionTable.getItems().setAll(transactionService.getAll());
    }

    @FXML
    private void goBackToDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
