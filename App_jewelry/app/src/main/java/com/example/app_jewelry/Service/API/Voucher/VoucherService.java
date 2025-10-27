package com.example.app_jewelry.Service.API.Voucher;

import com.example.app_jewelry.Service.DTO.reponse.VoucherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VoucherService {
    @GET("/api/vouchers/available")
    Call<List<VoucherResponse>> getAvailableVouchers(
            @Query("userId") Integer userId // có thể bỏ nếu không cần
    );
}
