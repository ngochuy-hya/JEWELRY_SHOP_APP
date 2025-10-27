package com.example.app_jewelry.Service.DTO.request;

public class BestsellerRequest {
    private Integer userId;

    public BestsellerRequest() {
    }

    public BestsellerRequest(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
