package com.example.API.DTO.Request;

import lombok.Data;

@Data
public class AddToCartRequest {
    private int userId;
    private int variantId;
    private int quantity;
}
