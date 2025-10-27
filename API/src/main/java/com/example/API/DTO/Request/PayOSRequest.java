package com.example.API.DTO.Request;

import lombok.Data;

@Data
public class PayOSRequest {
    private String orderId;
    private String description;
    private int amount;
    private String returnUrl;
    private String cancelUrl;
}