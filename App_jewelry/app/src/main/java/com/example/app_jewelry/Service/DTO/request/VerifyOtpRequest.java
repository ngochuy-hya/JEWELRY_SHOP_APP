package com.example.app_jewelry.Service.DTO.request;

public class VerifyOtpRequest {
    private String email;
    private String otp;

    public VerifyOtpRequest(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

}
