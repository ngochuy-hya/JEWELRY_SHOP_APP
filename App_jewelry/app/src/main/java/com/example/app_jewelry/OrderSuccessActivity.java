package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.adapter.OrderedProductAdapter;
import com.example.app_jewelry.entity.Address;
import com.example.app_jewelry.Service.DTO.reponse.CartItemResponse;

import java.util.ArrayList;
import java.util.List;

public class OrderSuccessActivity extends AppCompatActivity {

    private RecyclerView recyclerOrderedItems;
    private OrderedProductAdapter adapter;
    private List<CartItemResponse> orderedItemList = new ArrayList<>();

    private TextView tvName, tvPhone, tvEmail, tvFullAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        // Ánh xạ
        recyclerOrderedItems = findViewById(R.id.recyclerOrderedItems);
        TextView tvOrderId = findViewById(R.id.tvOrderId);
        TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);
        Button btnBackHome = findViewById(R.id.btnBackHome);

        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvFullAddress = findViewById(R.id.tvFullAddress);

        // Nhận dữ liệu từ Intent
        try {
            Intent intent = getIntent();
            orderedItemList = (List<CartItemResponse>) intent.getSerializableExtra("orderedItems");
            Address selectedAddress = (Address) intent.getSerializableExtra("selectedAddress");

            if (orderedItemList == null) orderedItemList = new ArrayList<>();

            // Setup RecyclerView
            adapter = new OrderedProductAdapter(this, orderedItemList);
            recyclerOrderedItems.setLayoutManager(new LinearLayoutManager(this));
            recyclerOrderedItems.setAdapter(adapter);

            tvOrderId.setText("Order #" + System.currentTimeMillis());
            tvTotalAmount.setText("Total: " + getTotalAmount() + "đ");

            // Hiển thị địa chỉ người nhận
            if (selectedAddress != null) {
                tvName.setText(selectedAddress.getReceiverName());
                tvPhone.setText(selectedAddress.getPhone());
                tvEmail.setText(selectedAddress.getEmail());

                String fullAddr = selectedAddress.getAddressLine() + ", " +
                        selectedAddress.getArea() + ", " +
                        selectedAddress.getCity() + ", " +
                        selectedAddress.getState();
                tvFullAddress.setText(fullAddr);
            } else {
                Log.e("OrderSuccess", "selectedAddress is null");
            }

        } catch (Exception e) {
            Log.e("OrderSuccessActivity", "Lỗi khi nhận dữ liệu từ Intent", e);
        }

        // Nút trở về trang chủ
        btnBackHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(OrderSuccessActivity.this, MainActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(homeIntent);
            finish();
        });
    }

    private String getTotalAmount() {
        double total = 0;
        for (CartItemResponse item : orderedItemList) {
            total += item.getPrice() * item.getQuantity();
        }
        return String.format("%,.0f", total);
    }
}
