package com.example.trackingfinances;

public class Operation {
    private int id;
    private int account_id;
    private int category_id;
    private int count;

    private static DataBaseHandler dataBaseHandler = new DataBaseHandler();

    public Operation(int id, int account_id, int category_id, int count) {
        this.id = id;
        this.account_id = account_id;
        this.category_id = category_id;
        this.count = count;
    }

    public static void addOperations(int accountId, int categoryId, int count) {
        dataBaseHandler.insertNewOperation(accountId, categoryId, count);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
