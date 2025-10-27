package com.example.app_jewelry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.VoucherResponse;
import com.example.app_jewelry.adapter.VoucherAdapter;
import com.example.app_jewelry.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckVoucherActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VoucherAdapter adapter;
    private List<VoucherResponse> voucherList = new ArrayList<>();
    public static final String EXTRA_SELECTED_VOUCHER_CODE = "selected_voucher_code";
    apiManager api = new apiManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voucher_check);

        // Áp inset padding cho toàn màn
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerVouchers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VoucherAdapter(voucherList, voucher -> {
            // Khi user chọn voucher
            Intent result = new Intent();
            result.putExtra(EXTRA_SELECTED_VOUCHER_CODE, voucher.getCode());
            setResult(Activity.RESULT_OK, result);
            finish();
        });

        recyclerView.setAdapter(adapter);

        loadVouchers(); // Giả lập hoặc gọi API

        // Xử lý nút back
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void loadVouchers() {
        int userId = new SharedPreferencesManager(this).getUserId();
        api.getAvailableVouchers(userId, new Callback<List<VoucherResponse>>() {
            @Override
            public void onResponse(Call<List<VoucherResponse>> call, Response<List<VoucherResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    voucherList.clear();
                    voucherList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<VoucherResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
