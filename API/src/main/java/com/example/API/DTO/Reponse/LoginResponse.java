package com.example.API.DTO.Reponse;

import lombok.Data;

@Data
public class LoginResponse {
    private Integer userId;
    private String token;

    public LoginResponse(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }

}
