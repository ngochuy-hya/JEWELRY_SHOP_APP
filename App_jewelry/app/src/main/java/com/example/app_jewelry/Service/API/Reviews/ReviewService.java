package com.example.app_jewelry.Service.API.Reviews;

import com.example.app_jewelry.Service.DTO.request.CreateReviewRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReviewService {
    @POST("reviews")
    Call<Void> submitReview(@Body CreateReviewRequest request);
}
