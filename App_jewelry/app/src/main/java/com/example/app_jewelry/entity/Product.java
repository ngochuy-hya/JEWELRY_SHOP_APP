package com.example.app_jewelry.entity;

import java.util.List;

public class Product {
    private int productId;
    private Integer categoryId;
    private Integer brandId;
    private String name;
    private String description;
    private String mainImage;
    private boolean isActive;

    // Constructors
    public Product() {}

    public Product(int productId, Integer categoryId, Integer brandId, String name, String description, String mainImage, boolean isActive) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.name = name;
        this.description = description;
        this.mainImage = mainImage;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
