package com.example.API.DTO.Request;

import lombok.Data;
import java.util.List;

@Data
public class CreateReviewRequest {
    private Integer userId;
    private Integer productId;
    private Integer rating;
    private String title;
    private String comment;
    private List<String> imageUrls;
}
