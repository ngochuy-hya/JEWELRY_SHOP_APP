package com.example.API.DTO.Reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BestsellerProductResponse {
    private Integer productId;
    private String productName;
    private String mainImage;
    private Long totalSold;
    private Double avgRating;
    private Long reviewCount;

    private Boolean favorite;
    private Double price;
    private Double oldPrice;
    public BestsellerProductResponse(
            Integer productId,
            String productName,
            String mainImage,
            Long totalSold,
            Double avgRating,
            Long reviewCount
    ) {
        this.productId = productId;
        this.productName = productName;
        this.mainImage = mainImage;
        this.totalSold = totalSold;
        this.avgRating = avgRating;
        this.reviewCount = reviewCount;
    }
}
