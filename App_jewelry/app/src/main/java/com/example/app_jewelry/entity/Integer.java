package com.example.app_jewelry.entity;

import java.util.List;

public class Integer {
    private int userId;
    private String fullName;
    private String email;
    private String passwordHash;
    private String phone;
    private Role role;
    private String createdAt;
    private List<Address> addresses;
    private Cart cart;

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public Cart getCart() {
        return cart;
    }
}
