package com.example.app_jewelry.Service.API.Favorite;

import com.example.app_jewelry.Service.DTO.reponse.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FavoriteService {
    @POST("favorites/add")
    Call<Void> addFavorite(@Query("userId") int userId, @Query("productId") int productId);

    @DELETE("favorites/remove")
    Call<Void> removeFavorite(@Query("userId") int userId, @Query("productId") int productId);
    @GET("favorites/{userId}")
    Call<List<ProductResponse>> getFavorites(@Path("userId") int userId);
}

