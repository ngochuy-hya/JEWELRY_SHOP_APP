package com.example.app_jewelry.Service.API.Cart;

import com.example.app_jewelry.Service.DTO.reponse.CartItemResponse;
import com.example.app_jewelry.Service.DTO.request.AddToCartRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartService {
    @GET("cart/my-items")
    Call<List<CartItemResponse>> getMyCartItems(@Query("userId") int userId);

    @PUT("cart/my-items/quantity")
    Call<Void> updateCartItemQuantity(
            @Query("userId") int userId,
            @Query("cartItemId") int cartItemId,
            @Query("quantity") int quantity
    );

    @DELETE("cart/my-items")
    Call<Void> deleteCartItem(
            @Query("userId") int userId,
            @Query("cartItemId") int cartItemId
    );
    @GET("cart/my-items/total-quantity")
    Call<Integer> getCartItemCount(@Query("userId") int userId);
    @POST("/api/cart/my-items")
    Call<Void> addToCart(@Body AddToCartRequest request);
}
