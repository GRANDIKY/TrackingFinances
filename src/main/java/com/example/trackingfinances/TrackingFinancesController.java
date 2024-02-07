package com.example.trackingfinances;


import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class TrackingFinancesController {

    @FXML
    private Label maxOut;

    @FXML
    private Label minOut;

    @FXML
    private Label maxIn;

    @FXML
    private Label minIn;

    @FXML
    private ComboBox<String> accComboBox;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TextField countTextField;

    @FXML
    private Button addOperationButton;

    @FXML
    private TableView<Account> accountTableView;

    @FXML
    private TableColumn<Account, String> nameAccTableColumn;

    @FXML
    private TableColumn<Account, Integer> balanceAccTableColumn;

    @FXML
    private TableView<DataTable> inTableView;

    @FXML
    private TableColumn<DataTable, String> inAccTableColumn;

    @FXML
    private TableColumn<DataTable, String> inCategoryTableColumn;

    @FXML
    private TableColumn<DataTable, Integer> inCountTableColumn;

    @FXML
    private TableView<DataTable> outTableView;

    @FXML
    private TableColumn<DataTable, String> outAccTableColumn;

    @FXML
    private TableColumn<DataTable, String> outCategoryTableColumn;

    @FXML
    private TableColumn<DataTable, Integer> outCountTableColumn;

    @FXML
    public void initialize() {

        ObservableList<String> nameCategories = Category.getNameCategory();
        categoryComboBox.setItems(nameCategories);

        ObservableList<String> nameAccounts = Account.getNameAccounts();
        accComboBox.setItems(nameAccounts);

        inAccTableColumn.setCellValueFactory(new PropertyValueFactory<>("account_name"));
        inCategoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("category_name"));
        inCountTableColumn.setCellValueFactory(new PropertyValueFactory<>("operations_count"));

        ObservableList<DataTable> inDataTable = DataTable.getInDataTable();

        inTableView.setItems(inDataTable);

        outAccTableColumn.setCellValueFactory(new PropertyValueFactory<>("account_name"));
        outCategoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("category_name"));
        outCountTableColumn.setCellValueFactory(new PropertyValueFactory<>("operations_count"));

        ObservableList<DataTable> outDataTable = DataTable.getOutDataTable();

        outTableView.setItems(outDataTable);

        nameAccTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        balanceAccTableColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        ObservableList<Account> accounts = Account.getAccounts();

        accountTableView.setItems(accounts);

        refreshLabels();

        accountTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    int selectedIndex = accountTableView.getSelectionModel().getSelectedIndex();
                    if (selectedIndex >= 0) {
                        Account selectedAccount = accountTableView.getItems().get(selectedIndex);
                        Account.deleteAccount(selectedAccount.getId());
                        accountTableView.getItems().remove(selectedIndex);

                        ObservableList<Account> accounts = Account.getAccounts();
                        accountTableView.setItems(accounts);

                        ObservableList<DataTable> inDataTable = DataTable.getInDataTable();
                        inTableView.setItems(inDataTable);

                        ObservableList<DataTable> outDataTable = DataTable.getOutDataTable();
                        outTableView.setItems(outDataTable);

                        refreshLabels();
                    }
                }
            }
        });
    }

    @FXML
    private void onAddOperationsButtonClicked() {
        Account selectedAccount = Account.getAccountByName(accComboBox.getSelectionModel().getSelectedItem());
        Category selectedCategory = Category.getCategoryByName(categoryComboBox.getSelectionModel().getSelectedItem());

        double currentBalance = selectedAccount.getBalance();
        double newBalance = currentBalance + Double.parseDouble(countTextField.getText());

        Operation.addOperations(selectedAccount.getId(), selectedCategory.getId(), Integer.parseInt(countTextField.getText()));
        Account.updateBalance(selectedAccount.getId(), newBalance);

        ObservableList<Account> accounts = Account.getAccounts();
        accountTableView.setItems(accounts);

        ObservableList<DataTable> inDataTable = DataTable.getInDataTable();
        inTableView.setItems(inDataTable);

        ObservableList<DataTable> outDataTable = DataTable.getOutDataTable();
        outTableView.setItems(outDataTable);

        accComboBox.getSelectionModel().clearSelection();
        categoryComboBox.getSelectionModel().clearSelection();
        countTextField.setText("");

        refreshLabels();
    }

    @FXML
    private void onAccButtonCLicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-account-view.fxml"));
            VBox newWindowLayout = loader.load();
            Stage newWindow = new Stage();
            newWindow.setTitle("Добавление счета");
            newWindow.setScene(new Scene(newWindowLayout));
            newWindow.showAndWait();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

        ObservableList<String> nameAccounts = Account.getNameAccounts();
        accComboBox.setItems(nameAccounts);

        ObservableList<Account> accounts = Account.getAccounts();
        accountTableView.setItems(accounts);

        refreshLabels();
    }

    private void refreshLabels() {
        int maxCountOut = outTableView.getItems().stream()
                .map(dataTable -> dataTable.getOperations_count())
                .min(Integer::compareTo)
                .orElse(0);
        int minCountOut = outTableView.getItems().stream()
                .map(dataTable -> dataTable.getOperations_count())
                .max(Integer::compareTo)
                .orElse(0);

        int maxCountIn = inTableView.getItems().stream()
                .map(dataTable -> dataTable.getOperations_count())
                .max(Integer::compareTo)
                .orElse(0);
        int minCountIn = inTableView.getItems().stream()
                .map(dataTable -> dataTable.getOperations_count())
                .min(Integer::compareTo)
                .orElse(0);

        DataTable maxOutDataTable = outTableView.getItems().stream()
                .filter(dataTable -> dataTable.getOperations_count() == maxCountOut)
                .findFirst().orElse(null);
        DataTable minOutDataTable = outTableView.getItems().stream()
                .filter(dataTable -> dataTable.getOperations_count() == minCountOut)
                .findFirst().orElse(null);

        DataTable minInDataTable = inTableView.getItems().stream()
                .filter(dataTable -> dataTable.getOperations_count() == minCountIn)
                .findFirst().orElse(null);
        DataTable maxInDataTable = inTableView.getItems().stream()
                .filter(dataTable -> dataTable.getOperations_count() == maxCountIn)
                .findFirst().orElse(null);

        if (maxOutDataTable != null) {
            maxOut.setText(maxOutDataTable.getCategory_name() + " " + maxCountOut);
        } else {
            maxOut.setText("Нет данных");
        }

        if (minOutDataTable != null) {
            minOut.setText(minOutDataTable.getCategory_name() + " " + minCountOut);
        } else {
            minOut.setText("Нет данных");
        }

        if (maxInDataTable != null) {
            maxIn.setText(maxInDataTable.getCategory_name() + " " + maxCountIn);
        }
        else {
            maxIn.setText("Нет данных");
        }

        if (minInDataTable != null) {
            minIn.setText(minInDataTable.getCategory_name() + " " + minCountIn);
        }
        else {
            minIn.setText("Нет данных");
        }
    }
}