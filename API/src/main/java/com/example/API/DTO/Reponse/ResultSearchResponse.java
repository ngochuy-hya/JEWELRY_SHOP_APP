package com.example.API.DTO.Reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultSearchResponse {
    private int productID;
    private String name;
    private double currentPrice;
    private String mainImage;
}

