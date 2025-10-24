package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.stage.Stage;
import service.AccountService;

public class InterestController {
    @FXML private Button applyInterestButton;

    private AccountService accountService = service.AppContext.getInstance().getAccountService();

    @FXML
    private void handleApplyInterest(ActionEvent event) {
        boolean applied = accountService.applyInterestAll();
        if (applied) {
            new Alert(Alert.AlertType.INFORMATION, "Interest applied to eligible accounts.").showAndWait();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "No eligible accounts for interest.").showAndWait();
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
