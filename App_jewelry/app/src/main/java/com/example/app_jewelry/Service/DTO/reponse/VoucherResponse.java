package com.example.app_jewelry.Service.DTO.reponse;

import java.io.Serializable;

public class VoucherResponse implements Serializable {
    private String code;
    private String description;
    private String discountType;
    private double discountValue;
    private Double maxDiscountValue;
    private double minOrderAmount;
    private String expiryDate;

    // Constructors
    public VoucherResponse() {
    }

    public VoucherResponse(String code, String description, String discountType, double discountValue,
                           Double maxDiscountValue, double minOrderAmount, String expiryDate) {
        this.code = code;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.maxDiscountValue = maxDiscountValue;
        this.minOrderAmount = minOrderAmount;
        this.expiryDate = expiryDate;
    }

    // Getters & Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public Double getMaxDiscountValue() {
        return maxDiscountValue;
    }

    public void setMaxDiscountValue(Double maxDiscountValue) {
        this.maxDiscountValue = maxDiscountValue;
    }

    public double getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(double minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}