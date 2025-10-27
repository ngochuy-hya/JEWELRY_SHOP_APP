package com.example.API.DTO.Reponse;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private Integer orderId;
    private String createdAt;
    private String status;
    private Double totalAmount;
    private String voucherCode;
    private List<OrderItemResponse> items;
}
