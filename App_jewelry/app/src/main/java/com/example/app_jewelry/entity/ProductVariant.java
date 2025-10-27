package com.example.app_jewelry.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductVariant implements Serializable {
    @SerializedName("variantId")
    private int variantID;
    private int productID;
    private String color;
    private String size;
    private double price;
    private int stock;

    public ProductVariant(int variantID, int productID, String color, String size, double price, int stock) {
        this.setVariantID(variantID);
        this.setProductID(productID);
        this.setColor(color);
        this.setSize(size);
        this.setPrice(price);
        this.setStock(stock);
    }

    public int getVariantID() {
        return variantID;
    }

    public void setVariantID(int variantID) {
        this.variantID = variantID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Getters and Setters
}
