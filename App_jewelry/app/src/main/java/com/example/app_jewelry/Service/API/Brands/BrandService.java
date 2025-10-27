package com.example.app_jewelry.Service.API.Brands;

import com.example.app_jewelry.entity.Brand;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BrandService {
    @GET("brands")
    Call<List<Brand>> getAllBrands();
}
