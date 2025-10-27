package com.example.app_jewelry.Service.API.Category;

import com.example.app_jewelry.entity.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    @GET("categories")
    Call<List<Category>> getAllCategories();
}
