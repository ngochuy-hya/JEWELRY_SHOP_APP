package com.example.API.DTO.Reponse;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BulkDealResponse {
    private Integer dealId;
    private String categoryName;
    private BigDecimal discountPercent;
    private Integer minQuantity;
    private String dealTitle;
    private String dealDescription;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
