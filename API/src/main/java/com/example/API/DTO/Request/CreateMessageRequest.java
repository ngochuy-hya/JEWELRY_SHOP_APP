package com.example.API.DTO.Request;

import lombok.Data;
import java.util.List;

@Data
public class CreateMessageRequest {
    private Integer conversationId;
    private String sender;
    private String content;
    private List<String> imageUrls;
}

