package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.StringConverter;
import model.Account;
import model.Customer;
import service.AccountService;

public class AccountController {
    @FXML private TableView<Account> accountTable;
    @FXML private TableColumn<Account, String> accountNumberColumn;
    @FXML private TableColumn<Account, String> branchColumn;
    @FXML private TableColumn<Account, Double> balanceColumn;
    @FXML private TableColumn<Account, String> ownerColumn;
    @FXML private Button openAccountButton;

    private AccountService accountService = service.AppContext.getInstance().getAccountService();

    @FXML
    private void initialize() {
        if (accountTable != null) {
            // Configure table columns
            accountNumberColumn.setCellValueFactory(cellData -> cellData.getValue().accountNumberProperty());
            branchColumn.setCellValueFactory(cellData -> cellData.getValue().branchProperty());
            balanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
            ownerColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getOwner().toString()));

            // Load accounts
            accountTable.getItems().setAll(accountService.getAllAccounts());
        }
    }

    @FXML
    private void handleOpenAccount(ActionEvent event) {
        Account selected = accountTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // placeholder: in real app open account details
            System.out.println("Open account: " + selected.getAccountNumber());
        }
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
