package com.example.app_jewelry.Service.API.Address;

import com.example.app_jewelry.entity.Address;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AddressService {
    @GET("addresses/user/{userId}")
    Call<List<Address>> getUserAddresses(@Path("userId") int userId);
    // Thêm địa chỉ mới
    @POST("addresses/add")
    Call<Address> addAddress(@Query("userId") int userId, @Body Address address);

    // Cập nhật địa chỉ
    @PUT("addresses/{addressId}")
    Call<Address> updateAddress(@Path("addressId") int addressId, @Body Address address);

    // Xoá địa chỉ
    @DELETE("addresses/{addressId}")
    Call<Void> deleteAddress(@Path("addressId") int addressId);

    // Đặt địa chỉ mặc định
    @POST("addresses/set-default")
    Call<Void> setDefaultAddress(@Query("userId") int userId, @Query("addressId") int addressId);
}
