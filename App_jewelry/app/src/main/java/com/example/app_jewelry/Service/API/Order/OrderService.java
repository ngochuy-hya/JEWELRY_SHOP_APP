package com.example.app_jewelry.Service.API.Order;

import com.example.app_jewelry.Service.DTO.reponse.OrderResponse;
import com.example.app_jewelry.Service.DTO.request.CreateOrderRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderService {
    @POST("orders")
    Call<Void> createOrder(@Body CreateOrderRequest request);
    @GET("orders/my")
    Call<List<OrderResponse>> getMyOrders(@Query("userId") int userId);

    @GET("orders/{id}")
    Call<OrderResponse> getOrderDetail(@Path("id") int orderId, @Query("userId") int userId);
    @GET("orders/has-bought")
    Call<Boolean> hasUserBoughtProduct(@Query("userId") int userId, @Query("productId") int productId);


}
