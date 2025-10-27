package com.example.app_jewelry.entity;

import java.io.Serializable;
import java.lang.Integer;

public class Address implements Serializable {
    private Integer addressId;
    private int userId;
    private String receiverName;
    private String phone;
    private String email;
    private String addressLine;
    private String area;
    private String landmark;
    private String city;
    private String state;
    private boolean isDefault;
    private String createdAt;

    // Constructor mặc định
    public Address() {}

    // Constructor đầy đủ
    public Address(Integer addressId, int userId, String receiverName, String phone, String email,
                   String addressLine, String area, String landmark,
                   String city, String state, boolean isDefault, String createdAt) {
        this.setAddressId(addressId);
        this.userId = userId;
        this.receiverName = receiverName;
        this.phone = phone;
        this.email = email;
        this.addressLine = addressLine;
        this.area = area;
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
    }

    // Getter + Setter

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}
