package com.example.trackingfinances;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseHandler {

    private static final String DATABASE_URL = "jdbc:sqlite:./src/main/resources/com/example/trackingfinances/tracking_finances_db.db";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public ObservableList<Account> selectCashAccounts() {
        String sql = "SELECT id, name, balance FROM cash_account";
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        try (Statement preparedStatement = getConnection().createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
            while (resultSet.next()) {
                accounts.add(new Account(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("balance")));
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        return accounts;
    }

    public ArrayList<Operation> selectOperations() {
        String sql = "SELECT id, account_id, category_id, count FROM operations";
        ArrayList<Operation> operations = new ArrayList<>();
        try (Statement preparedStatement = getConnection().createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
            while (resultSet.next()) {
                operations.add(new Operation(resultSet.getInt("id"),
                        resultSet.getInt("account_id"),
                        resultSet.getInt("category_id"),
                        resultSet.getInt("count")));
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        return operations;
    }

    public ArrayList<Category> selectCategory() {
        String sql = "SELECT id, name FROM category";
        ArrayList<Category> categories = new ArrayList<>();
        try (Statement preparedStatement = getConnection().createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
            while (resultSet.next()) {
                categories.add(new Category(resultSet.getInt("id"),
                        resultSet.getString("name")));
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        return categories;
    }

    public void addCashAccount(String name, int balance) {
        String sql = "INSERT INTO cash_account (name, balance) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, balance);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public ObservableList<DataTable> selectInDataTable() {
        String sql = "SELECT \n" +
                "    cash_account.name AS account_name,\n" +
                "    category.name AS category_name,\n" +
                "    operations.count AS operations_count\n" +
                "FROM \n" +
                "    cash_account\n" +
                "JOIN \n" +
                "    operations ON cash_account.id = operations.account_id\n" +
                "JOIN \n" +
                "    category ON operations.category_id = category.id\n" +
                "WHERE \n" +
                "    operations.count > 0\n" +
                "ORDER BY \n" +
                "    cash_account.name, \n" +
                "    category.name, \n" +
                "    operations.count";
        ObservableList<DataTable> dataTable = FXCollections.observableArrayList();
        try (Statement preparedStatement = getConnection().createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
            while (resultSet.next()) {
                dataTable.add(new DataTable(resultSet.getString("account_name"),
                        resultSet.getString("category_name"),
                        resultSet.getInt("operations_count")));
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        return dataTable;
    }

    public ObservableList<DataTable> selectOutDataTable() {
        String sql = "SELECT \n" +
                "    cash_account.name AS account_name,\n" +
                "    category.name AS category_name,\n" +
                "    operations.count AS operations_count\n" +
                "FROM \n" +
                "    cash_account\n" +
                "JOIN \n" +
                "    operations ON cash_account.id = operations.account_id\n" +
                "JOIN \n" +
                "    category ON operations.category_id = category.id\n" +
                "WHERE \n" +
                "    operations.count < 0\n" +
                "ORDER BY \n" +
                "    cash_account.name, \n" +
                "    category.name, \n" +
                "    operations.count";
        ObservableList<DataTable> dataTable = FXCollections.observableArrayList();
        try (Statement preparedStatement = getConnection().createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
            while (resultSet.next()) {
                dataTable.add(new DataTable(resultSet.getString("account_name"),
                        resultSet.getString("category_name"),
                        resultSet.getInt("operations_count")));
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        return dataTable;
    }

    public void deleteAccountFromDB(int id) {
        String sql = "DELETE FROM cash_account\n" +
                "WHERE id = ?;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public ObservableList<String> selectNameAccounts() {
        String sql = "SELECT name FROM cash_account";
        ObservableList<String> name = FXCollections.observableArrayList();
        try (Statement preparedStatement = getConnection().createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
            while (resultSet.next()) {
                name.add(resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        return name;
    }

    public ObservableList<String> selectNameCategory() {
        String sql = "SELECT name FROM category";
        ObservableList<String> name = FXCollections.observableArrayList();
        try (Statement preparedStatement = getConnection().createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
            while (resultSet.next()) {
                name.add(resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        return name;
    }

    public Account selectAccountByName(String accountName) {
        String sql = "SELECT id, name, balance FROM cash_account WHERE name = '"+ accountName +"'";
        Account account = null;
        try (Statement preparedStatement = getConnection().createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
            account = new Account(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getInt("balance"));
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        return account;
    }

    public Category selectCategoryByName(String categoryName) {
        String sql = "SELECT id, name FROM category WHERE name = '"+ categoryName +"'";
        Category category = null;
        try (Statement preparedStatement = getConnection().createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
            category = new Category(resultSet.getInt("id"), resultSet.getString("name"));
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        return category;
    }

    public void insertNewOperation(int accountId, int categoryId, int count) {
        String sql = "INSERT INTO operations (account_id, category_id, count) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.setInt(3, count);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public void updateBalanceAccount(int id, double newBalance) {
        String sql = "UPDATE cash_account SET balance = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
