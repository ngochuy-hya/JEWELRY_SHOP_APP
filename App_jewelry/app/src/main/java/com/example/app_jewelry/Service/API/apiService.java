package com.example.app_jewelry.Service.API;

import com.example.app_jewelry.entity.District;
import com.example.app_jewelry.entity.Province;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface apiService {
    @GET("api/p/")
    Call<List<Province>> getAllProvinces();

    @GET("api/p/{province_code}")
    Call<Province> getProvinceWithDistricts(@Path("province_code") int provinceCode, @Query("depth") int depth);

    @GET("api/d/{district_code}")
    Call<District> getDistrictWithWards(@Path("district_code") int districtCode, @Query("depth") int depth);
}
