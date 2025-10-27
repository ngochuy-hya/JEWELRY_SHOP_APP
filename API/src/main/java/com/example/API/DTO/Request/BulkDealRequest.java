package com.example.API.DTO.Request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BulkDealRequest {
    private Integer categoryId;
    private BigDecimal discountPercent;
    private Integer minQuantity;
    private String dealTitle;
    private String dealDescription;
    private Boolean isActive;
}
