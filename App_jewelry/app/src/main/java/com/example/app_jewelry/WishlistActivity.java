package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.ProductResponse;
import com.example.app_jewelry.adapter.ProductAdapter;
import com.example.app_jewelry.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistActivity extends AppCompatActivity {

    private RecyclerView recyclerWishlist;
    private ImageView btnBack;
    private final apiManager api = new apiManager();
    private final List<ProductResponse> favoriteList = new ArrayList<>();
    private ProductAdapter adapter;
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wishlist);

        // Lấy userId từ SharedPreferences
        userId = new SharedPreferencesManager(this).getUserId();
        if (userId == -1 || userId == 0) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerWishlist = findViewById(R.id.recyclerWishlist);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        recyclerWishlist.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerWishlist.setHasFixedSize(true);

        adapter = new ProductAdapter(favoriteList, this, (product, position) -> {
            api.removeFavorite(userId, product.getProductID(), new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    favoriteList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(WishlistActivity.this, "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(WishlistActivity.this, "Lỗi khi xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                }
            });
        });

        recyclerWishlist.setAdapter(adapter);

        loadFavoriteProducts();
    }

    private void loadFavoriteProducts() {
        api.getFavoriteProducts(userId, new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    favoriteList.clear();

                    for (ProductResponse product : response.body()) {
                        product.setFavorite(true);
                        favoriteList.add(product);
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(WishlistActivity.this, "Không tìm thấy dữ liệu yêu thích", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                Toast.makeText(WishlistActivity.this, "Lỗi khi tải dữ liệu yêu thích", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
