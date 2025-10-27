package com.example.app_jewelry.Service.API.FlashDeal;

import com.example.app_jewelry.Service.DTO.reponse.TodayOfferResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FlashDealService {
    @GET("flash-deal/today-offers")
    Call<List<TodayOfferResponse>> getTodayOffers();
}
