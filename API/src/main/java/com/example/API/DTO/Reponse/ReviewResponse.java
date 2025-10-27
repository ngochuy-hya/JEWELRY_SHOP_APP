package com.example.API.DTO.Reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {
    private String userName;
    private String comment;
    private int rating;
    private String title;
    private LocalDateTime createdAt;
    private List<String> imageUrls;
}
