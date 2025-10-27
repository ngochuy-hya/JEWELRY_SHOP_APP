package com.example.app_jewelry.Service.API.Product;



import com.example.app_jewelry.Service.DTO.reponse.BestsellerProductResponse;
import com.example.app_jewelry.Service.DTO.reponse.FilterOptionsResponse;
import com.example.app_jewelry.Service.DTO.reponse.ProductArrivalResponse;
import com.example.app_jewelry.Service.DTO.reponse.ProductDetailResponse;
import com.example.app_jewelry.Service.DTO.reponse.ProductImageResponse;
import com.example.app_jewelry.Service.DTO.reponse.ProductResponse;
import com.example.app_jewelry.Service.DTO.reponse.ResultSearchResponse;
import com.example.app_jewelry.Service.DTO.request.BestsellerRequest;
import com.example.app_jewelry.Service.DTO.request.ProductArrivalRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {

    @POST("products/arrivals")
    Call<List<ProductArrivalResponse>> getProductArrivals(@Body ProductArrivalRequest request);

    @POST("products/bestsellers")
    Call<List<BestsellerProductResponse>> getBestsellerProducts(@Body BestsellerRequest request);


    @GET("products/brand/{brandId}")
    Call<List<ProductResponse>> getProductsByBrand(
            @Path("brandId") int brandId,
            @Query("userId") Integer userId,
            @Query("sortOption") String sortOption
    );

    @GET("products/category/{categoryId}")
    Call<List<ProductResponse>> getProductsByCategory(
            @Path("categoryId") int categoryId,
            @Query("userId") Integer userId,
            @Query("sortOption") String sortOption
    );



    @GET("products/list")
    Call<List<ProductResponse>> getProductsByType(
            @Query("type") String type,
            @Query("userId") Integer userId,
            @Query("sortOption") String sortOption
    );

    @GET("products/{productId}/images")
    Call<ProductImageResponse> getProductImages(
            @Path("productId") int productId
    );
    @GET("products/{productId}/detail")
    Call<ProductDetailResponse> getProductDetail(
            @Path("productId") int productId,
            @Query("userId") Integer userId
    );
    @GET("/api/products/filters")
    Call<FilterOptionsResponse> getFilterOptions();
    @GET("products/filter")
    Call<List<ProductResponse>> filterProducts(
            @Query("brands") List<String> brands,
            @Query("categories") List<String> categories,
            @Query("sizes") List<String> sizes,
            @Query("minPrice") Float minPrice,
            @Query("maxPrice") Float maxPrice,
            @Query("sort") String sortOption,
            @Query("userId") Integer userId
    );
    @POST("/api/products/by-ids")
    Call<List<ProductResponse>> getProductsByIds(@Body List<Integer> productIds, @Query("userId") int userId);
    @GET("products/search")
    Call<List<ResultSearchResponse>> searchProducts(
            @Query("query") String query
    );

}
