package com.example.API.DTO.Request;

public class ProductArrivalRequest {
    private Integer userId;

    public ProductArrivalRequest() {
    }

    public ProductArrivalRequest(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}