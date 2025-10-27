package com.example.API.DTO.Reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private int messageId;
    private String sender;
    private String content;
    private String sentAt;
    private List<String> imageUrls;
}
