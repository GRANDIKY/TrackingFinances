package com.example.trackingfinances;

import javafx.collections.ObservableList;

public class DataTable {
    private String account_name;
    private String category_name;
    private int operations_count;

    private static DataBaseHandler dataBaseHandler = new DataBaseHandler();

    public DataTable(String account_name, String category_name, int operations_count) {
        this.account_name = account_name;
        this.category_name = category_name;
        this.operations_count = operations_count;
    }

    public static ObservableList<DataTable> getInDataTable() {
        return dataBaseHandler.selectInDataTable();
    }

    public static ObservableList<DataTable> getOutDataTable() {
        return dataBaseHandler.selectOutDataTable();
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getOperations_count() {
        return operations_count;
    }

    public void setOperations_count(int operations_count) {
        this.operations_count = operations_count;
    }

    public static DataBaseHandler getDataBaseHandler() {
        return dataBaseHandler;
    }

    public static void setDataBaseHandler(DataBaseHandler dataBaseHandler) {
        DataTable.dataBaseHandler = dataBaseHandler;
    }
}
