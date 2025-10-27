package com.example.app_jewelry.Service.DTO.request;

import java.util.List;

public class CreateMessageRequest {
    private Integer conversationId;
    private String sender;
    private String content;
    private List<String> imageUrls;
    public CreateMessageRequest(Integer conversationId,String sender,String content,List<String> imageUrls)
    {
        this.conversationId = conversationId;
        this.sender = sender;
        this.content = content;
        this.imageUrls = imageUrls;
    }
}

