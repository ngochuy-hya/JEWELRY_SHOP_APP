package com.example.app_jewelry.Service.DTO.request;

public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;

    // Getters & Setters
    public String getOldPassword() { return oldPassword; }
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
