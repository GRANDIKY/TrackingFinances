package com.example.trackingfinances;

import javafx.collections.ObservableList;

public class Account {

    private int id;
    private String name;
    private double balance;
    private static DataBaseHandler dataBaseHandler = new DataBaseHandler();

    public Account(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public static Account getAccountByName(String accountName) {
        return dataBaseHandler.selectAccountByName(accountName);
    }

    public static ObservableList<String> getNameAccounts() {
        return dataBaseHandler.selectNameAccounts();
    }

    public static ObservableList<Account> getAccounts() {
        return dataBaseHandler.selectCashAccounts();
    }

    public static void deleteAccount(int id) {
        dataBaseHandler.deleteAccountFromDB(id);
    }

    public static void updateBalance(int id, double newBalance){
        dataBaseHandler.updateBalanceAccount(id, newBalance);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
