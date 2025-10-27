package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.OrderItemResponse;
import com.example.app_jewelry.Service.DTO.reponse.OrderResponse;
import com.example.app_jewelry.adapter.OrderAdapter;
import com.example.app_jewelry.entity.Order;
import com.example.app_jewelry.entity.OrderItem;
import com.example.app_jewelry.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderActivity extends AppCompatActivity {

    private FrameLayout orderContainer;
    private apiManager api = new apiManager();
    private ImageView btnBack;
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        SharedPreferencesManager spManager = new SharedPreferencesManager(this);
        userId = spManager.getUserId();

        // Nếu chưa đăng nhập => chuyển sang LoginActivity
        if (userId == -1 || userId == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        initToolbar();

        orderContainer = findViewById(R.id.orderContainer);

        fetchOrdersFromApi();
    }

    private void initToolbar() {
        ImageView btnBack = findViewById(R.id.btnBack);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("My Orders");
        btnBack.setOnClickListener(v -> finish());
    }

    private void fetchOrdersFromApi() {
        api.getMyOrders(userId, new Callback<List<OrderResponse>>() {
            @Override
            public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Order> orderList = convertToEntity(response.body());
                    if (orderList.isEmpty()) {
                        showEmptyView();
                    } else {
                        showOrderList(orderList);
                    }
                } else {
                    showEmptyView();
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MyOrderActivity.this, "Failed to load orders", Toast.LENGTH_SHORT).show();
                showEmptyView();
            }
        });
    }

    private List<Order> convertToEntity(List<OrderResponse> responses) {
        List<Order> result = new ArrayList<>();
        for (OrderResponse res : responses) {
            List<OrderItem> items = new ArrayList<>();
            for (OrderItemResponse i : res.getItems()) {
                items.add(new OrderItem(i.getVariantId(), i.getProductName(),
                        i.getUnitPrice(), i.getQuantity(), i.getImageUrl()));
            }
            Order order = new Order();
            order.setOrderId(res.getOrderId());
            order.setStatus(res.getStatus());
            order.setCreatedAt(res.getCreatedAt());
            order.setTotalAmount(res.getTotalAmount());
            order.setItems(items);
            result.add(order);
        }
        return result;
    }

    private void showOrderList(List<Order> orders) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View listView = inflater.inflate(R.layout.fragment_order_list, orderContainer, false);

        RecyclerView recyclerView = listView.findViewById(R.id.recyclerOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter adapter = new OrderAdapter(this, orders, order -> {
            Intent intent = new Intent(MyOrderActivity.this, OrderDetailActivity.class);
            intent.putExtra("orderId", order.getOrderId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        orderContainer.removeAllViews();
        orderContainer.addView(listView);
    }

    private void showEmptyView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View emptyView = inflater.inflate(R.layout.fragment_order_empty, orderContainer, false);
        Button btnContinue = emptyView.findViewById(R.id.btnContinueShopping);
        btnContinue.setOnClickListener(v -> finish()); // hoặc chuyển về MainActivity nếu cần
        orderContainer.removeAllViews();
        orderContainer.addView(emptyView);
    }
}
