package com.example.API.DTO.Reponse;

import com.example.API.Entity.ProductVariant;
import com.example.API.Entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductDetailResponse {
    private int productId;
    private String name;
    private String description;
    private String mainImage;
    private List<String> additionalImages;

    private double price;
    private double originalPrice;
    private double discountPercent;

    private float averageRating;
    private int reviewCount;
    private boolean isFavorite;

    private String brandName;
    private String categoryDescription;

    private List<ProductVariantResponse> variants;
    private List<ReviewResponse> reviews;
    private Map<Integer, Integer> starCount;
}

