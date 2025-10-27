package com.example.app_jewelry.Service.DTO.request;

// AddToCartRequest.java
public class AddToCartRequest {
    private int userId;
    private int variantId;
    private int quantity;

    public AddToCartRequest(int userId, int variantId, int quantity) {
        this.setUserId(userId);
        this.setVariantId(variantId);
        this.setQuantity(quantity);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
