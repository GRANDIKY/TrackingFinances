package com.example.trackingfinances;

import javafx.collections.ObservableList;

public class Category {
    private int id;
    private String name;
    private static DataBaseHandler dataBaseHandler = new DataBaseHandler();
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category getCategoryByName(String categoryName) {
        return dataBaseHandler.selectCategoryByName(categoryName);
    }
    public static ObservableList<String> getNameCategory() {
        return dataBaseHandler.selectNameCategory();
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
}
