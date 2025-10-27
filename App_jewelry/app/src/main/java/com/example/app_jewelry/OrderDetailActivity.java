package com.example.app_jewelry;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.OrderItemResponse;
import com.example.app_jewelry.Service.DTO.reponse.OrderResponse;
import com.example.app_jewelry.adapter.OrderItemAdapter;
import com.example.app_jewelry.utils.SharedPreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView tvOrderStatus, tvOrderDate, tvTotalAmount;
    private RecyclerView recyclerItems;
    private ImageView btnBack;
    private final apiManager api = new apiManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_detail);

        initViews();

        btnBack.setOnClickListener(v -> finish());

        int orderId = getIntent().getIntExtra("orderId", -1);
        int userId = new SharedPreferencesManager(this).getUserId(); // ✅ lấy từ sharedPreferences

        if (orderId != -1 && userId > 0) {
            api.getOrderDetail(orderId, userId, new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        bindOrderData(response.body());
                    } else {
                        Toast.makeText(OrderDetailActivity.this, "Không thể tải chi tiết đơn hàng", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {
                    Toast.makeText(OrderDetailActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Không tìm thấy đơn hàng", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initViews() {
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        recyclerItems = findViewById(R.id.recyclerOrderItems);
        btnBack = findViewById(R.id.btnBack);
    }

    private void bindOrderData(OrderResponse order) {
        tvOrderStatus.setText("Status: " + order.getStatus());
        tvOrderDate.setText("Order Date: " + order.getCreatedAt());

        double total = 0;
        List<OrderItemResponse> items = order.getItems();
        for (OrderItemResponse item : items) {
            total += item.getUnitPrice() * item.getQuantity();
        }

        tvTotalAmount.setText("Total: ₫" + String.format("%,.0f", total));

        recyclerItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerItems.setAdapter(new OrderItemAdapter(this, items));
    }
}
