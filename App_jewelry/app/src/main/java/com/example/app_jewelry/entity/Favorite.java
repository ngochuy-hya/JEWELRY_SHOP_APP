package com.example.app_jewelry.entity;

public class Favorite {
    private int favoriteID;
    private int userID;
    private int productID;

    // Constructors
    public Favorite() {}

    public Favorite(int favoriteID, int userID, int productID) {
        this.favoriteID = favoriteID;
        this.userID = userID;
        this.productID = productID;
    }

    // Getters and Setters
    public int getFavoriteID() {
        return favoriteID;
    }

    public void setFavoriteID(int favoriteID) {
        this.favoriteID = favoriteID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
