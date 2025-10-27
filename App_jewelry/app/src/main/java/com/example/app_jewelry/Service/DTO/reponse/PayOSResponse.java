package com.example.app_jewelry.Service.DTO.reponse;

import com.google.gson.JsonObject;

public class PayOSResponse {
    private int error;
    private String message;
    private JsonObject data;
    private long orderCode;

    public int getError() { return error; }
    public String getMessage() { return message; }
    public JsonObject getData() { return data; }

    public long getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(long orderCode) {
        this.orderCode = orderCode;
    }
}
