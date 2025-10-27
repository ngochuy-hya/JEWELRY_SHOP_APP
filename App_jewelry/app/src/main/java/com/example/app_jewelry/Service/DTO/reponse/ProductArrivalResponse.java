package com.example.app_jewelry.Service.DTO.reponse;

import com.google.gson.annotations.SerializedName;

public class ProductArrivalResponse {
    private int productId;
    private String name;
    private String mainImage;
    private double price;
    private Double oldPrice;
    @SerializedName("favorite")
    private boolean isFavorite;

    // Constructors
    public ProductArrivalResponse() {
    }

    public ProductArrivalResponse(int productId, String name, String mainImage, double price, Double oldPrice, boolean isFavorite) {
        this.productId = productId;
        this.name = name;
        this.mainImage = mainImage;
        this.price = price;
        this.oldPrice = oldPrice;
        this.isFavorite = isFavorite;
    }

    // Getters & Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
