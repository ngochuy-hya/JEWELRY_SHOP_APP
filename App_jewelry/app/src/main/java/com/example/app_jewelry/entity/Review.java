package com.example.app_jewelry.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Review {
    private int reviewId;
    private User user;
    @SerializedName("userName")
    private String userName;
    private int rating;
    private String title;
    private String comment;
    private String createdAt;

    private List<String> imageUrls;

    public List<ReviewImage> getImageList() {
        if (imageUrls == null) return Collections.emptyList();
        List<ReviewImage> list = new ArrayList<>();
        for (String url : imageUrls) {
            list.add(new ReviewImage(url));
        }
        return list;
    }
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title != null ? title : "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment != null ? comment : "";
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getReviewerName() {
        return userName != null ? userName : "Ẩn danh";
    }

    public String getAvatarText() {
        return (userName != null && !userName.isEmpty()) ? String.valueOf(userName.charAt(0)).toUpperCase() : "?";
    }
}
