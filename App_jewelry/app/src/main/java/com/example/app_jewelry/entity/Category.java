package com.example.app_jewelry.entity;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("categoryId")
    private int categoryID;

    private String name;
    private String description;

    @SerializedName("imageUrl")
    private String imageUrl;

    public Category() {}

    public Category(int categoryID, String name, String description, String imageUrl) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
