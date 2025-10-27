package com.example.API.DTO.Reponse;

import lombok.Data;

@Data
public class CartItemResponse {
    private Integer cartItemId;
    private Integer variantId;
    private String sku;
    private String productName;
    private String imageUrl;

    private double price;
    private double oldPrice;
    private int discountPercent;
    private double dealDiscount;
    private int stock;
    private int quantity;
}
