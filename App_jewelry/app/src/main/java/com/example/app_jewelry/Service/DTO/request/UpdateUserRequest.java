package com.example.app_jewelry.Service.DTO.request;

public class UpdateUserRequest {
    private String fullName;
    private String phone;

    // Getters & Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}