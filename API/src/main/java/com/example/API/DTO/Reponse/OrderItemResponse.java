package com.example.API.DTO.Reponse;

import lombok.Data;

@Data
public class OrderItemResponse {
    private Integer variantId;
    private String productName;
    private Double unitPrice;
    private Integer quantity;
    private String imageUrl;
}

