package com.example.app_jewelry.Service.API.User;

import com.example.app_jewelry.Service.DTO.reponse.UserResponse;
import com.example.app_jewelry.Service.DTO.request.ChangePasswordRequest;
import com.example.app_jewelry.Service.DTO.request.UpdateUserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @GET("users/{id}")
    Call<UserResponse> getUserById(@Path("id") int userId);

    @PUT("users/{id}")
    Call<Void> updateUser(@Path("id") int userId, @Body UpdateUserRequest request);

    @PUT("users/{id}/change-password")
    Call<Void> changePassword(@Path("id") int userId, @Body ChangePasswordRequest request);
}
