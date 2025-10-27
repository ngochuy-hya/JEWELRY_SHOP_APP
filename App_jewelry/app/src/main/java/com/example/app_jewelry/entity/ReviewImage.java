package com.example.app_jewelry.entity;

public class ReviewImage {
    private int imageId;
    private int reviewId;
    private String imageUrl;
    private String createdAt;
    public ReviewImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public ReviewImage(int imageId, int reviewId, String imageUrl, String createdAt) {
        this.imageId = imageId;
        this.reviewId = reviewId;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public int getImageId() {
        return imageId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
