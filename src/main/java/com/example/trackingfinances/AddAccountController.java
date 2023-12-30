package com.example.trackingfinances;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddAccountController {

    @FXML
    private Button addAccountButton;

    @FXML
    private TextField nameAccount;

    @FXML
    private TextField balanceAccount;

    @FXML
    public void onAddButtonClick() {
        Stage stage = (Stage) addAccountButton.getScene().getWindow();
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.addCashAccount(nameAccount.getText(), Integer.parseInt(balanceAccount.getText()));
        stage.close();
    }
}
