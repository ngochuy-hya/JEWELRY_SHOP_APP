package com.example.app_jewelry.Service.API.Auth;


import com.example.app_jewelry.Service.DTO.reponse.LoginResponse;
import com.example.app_jewelry.Service.DTO.reponse.RegisterResponse;
import com.example.app_jewelry.Service.DTO.request.LoginRequest;
import com.example.app_jewelry.Service.DTO.request.RegisterRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("/api/auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @POST("/api/auth/verify-otp")
    Call<RegisterResponse> verifyOtp(@Body Map<String, String> otpBody);

    @POST("/api/auth/forgot-password")
    Call<RegisterResponse> forgotPassword(@Body Map<String, String> emailBody);

    @POST("/api/auth/reset-password")
    Call<RegisterResponse> resetPassword(@Body Map<String, String> resetBody);
}
