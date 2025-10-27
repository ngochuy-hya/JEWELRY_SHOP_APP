package com.example.app_jewelry.entity;

import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private int addressId;
    private String status;
    private double totalAmount;
    private String voucherCode;

    private String createdAt;
    private List<OrderItem> items;
    public Order() {}

    public Order(int userId, int addressId, String status, double totalAmount, List<OrderItem> items) {
        this.setUserId(userId);
        this.setAddressId(addressId);
        this.setStatus(status);
        this.setTotalAmount(totalAmount);
        this.setItems(items);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
}

