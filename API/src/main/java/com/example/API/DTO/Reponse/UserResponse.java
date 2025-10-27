package com.example.API.DTO.Reponse;

import lombok.Data;

@Data
public class UserResponse {
    private Integer userId;
    private String fullName;
    private String email;
    private String phone;
}