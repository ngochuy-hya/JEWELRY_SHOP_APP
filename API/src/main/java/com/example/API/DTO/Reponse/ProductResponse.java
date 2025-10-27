package com.example.API.DTO.Reponse;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductResponse {
    private int productID;
    private String name;
    private String mainImage;
    private double originalPrice;
    private double currentPrice;
    private double rating;
    private long reviewCount;
    private boolean isFavorite;

}
