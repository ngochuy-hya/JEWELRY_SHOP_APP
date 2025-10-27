package com.example.app_jewelry.entity;

public class BulkDeals {
    private int dealID;
    private int productID;
    private double discountPercent;
    private int minQuantity;
    private String dealTitle;
    private String dealDescription;
    private boolean isActive;
    private String createdAt;

    // Nếu dùng Android: có thể thêm ảnh từ Product (join) → ví dụ:
    private String productName;
    private String productImageUrl;

    // Constructors
    public BulkDeals() {}

    public BulkDeals(int dealID, int productID, double discountPercent, int minQuantity,
                     String dealTitle, String dealDescription, boolean isActive,
                     String createdAt, String productImageUrl) {
        this.dealID = dealID;
        this.productID = productID;
        this.discountPercent = discountPercent;
        this.minQuantity = minQuantity;
        this.dealTitle = dealTitle;
        this.dealDescription = dealDescription;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.productImageUrl = productImageUrl;
    }


    // Getters and setters
    public int getDealID() {
        return dealID;
    }

    public void setDealID(int dealID) {
        this.dealID = dealID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public String getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Nếu bạn join với Product thì thêm:
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
}
