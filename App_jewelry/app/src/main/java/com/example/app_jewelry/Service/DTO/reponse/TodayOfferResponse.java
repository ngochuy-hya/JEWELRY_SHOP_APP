package com.example.app_jewelry.Service.DTO.reponse;

import com.google.gson.annotations.SerializedName;

public class TodayOfferResponse {

    @SerializedName("productId")
    private int productId;

    @SerializedName("name")
    private String name;

    @SerializedName("mainImage")
    private String mainImage;

    @SerializedName("currentPrice")
    private double currentPrice;

    @SerializedName("originalPrice")
    private double originalPrice;

    @SerializedName("rating")
    private Double rating;

    @SerializedName("reviewCount")
    private Long reviewCount;

    // Constructor (optional)
    public TodayOfferResponse() {}

    public TodayOfferResponse(int productId, String name, String mainImage, double currentPrice, double originalPrice, double rating, long reviewCount) {
        this.productId = productId;
        this.name = name;
        this.mainImage = mainImage;
        this.currentPrice = currentPrice;
        this.originalPrice = originalPrice;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

    // Getters and Setters
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

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(long reviewCount) {
        this.reviewCount = reviewCount;
    }
}
