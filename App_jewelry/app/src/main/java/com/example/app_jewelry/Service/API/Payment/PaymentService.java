package com.example.app_jewelry.Service.API.Payment;

import com.example.app_jewelry.Service.DTO.reponse.PayOSResponse;
import com.example.app_jewelry.Service.DTO.reponse.PaymentStatusResponse;
import com.example.app_jewelry.Service.DTO.request.PayOSRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PaymentService {
    @POST("/api/payment/create")
    Call<PayOSResponse> createPayment(@Body PayOSRequest request);
    @GET("/api/payment/status/{orderId}")
    Call<PaymentStatusResponse> getPaymentStatus(@Path("orderId") long orderId);

}
