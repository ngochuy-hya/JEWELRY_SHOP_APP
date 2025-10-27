package com.example.app_jewelry.entity;

import com.google.gson.annotations.SerializedName;

public class Slider {

    @SerializedName("sliderId")
    private int id;

    private String title;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("linkUrl")
    private String linkUrl;

    private int displayOrder;
    private boolean isActive;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getImageUrl() { return imageUrl; }
    public String getLinkUrl() { return linkUrl; }
    public int getDisplayOrder() { return displayOrder; }
    public boolean isActive() { return isActive; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }

    // Constructor
    public Slider(int id, String title, String imageUrl, String linkUrl, int displayOrder, boolean isActive) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
        this.displayOrder = displayOrder;
        this.isActive = isActive;
    }
}
