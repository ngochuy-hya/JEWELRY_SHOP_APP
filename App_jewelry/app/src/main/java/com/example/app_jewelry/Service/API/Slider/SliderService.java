package com.example.app_jewelry.Service.API.Slider;

import com.example.app_jewelry.entity.Slider;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SliderService {
    @GET("sliders")
    Call<List<Slider>> getSliders();
}
