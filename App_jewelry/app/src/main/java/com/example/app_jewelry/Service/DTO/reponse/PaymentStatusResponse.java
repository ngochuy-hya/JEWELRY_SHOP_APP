package com.example.app_jewelry.Service.DTO.reponse;



import com.google.gson.annotations.SerializedName;

public class PaymentStatusResponse {

    @SerializedName("error")
    private int error;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    // Getter và Setter
    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
