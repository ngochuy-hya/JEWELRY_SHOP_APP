package com.example.app_jewelry.Service.DTO.reponse;

public class OrderItemResponse {
    private int variantId;
    private String productName;
    private double unitPrice;
    private int quantity;
    private String imageUrl;

    public OrderItemResponse() {}

    public OrderItemResponse(int variantId, String productName,
                             double unitPrice, int quantity, String imageUrl) {
        this.variantId = variantId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
