package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.ProductResponse;
import com.example.app_jewelry.adapter.ProductAdapter;
import com.example.app_jewelry.fragment.FilterDialogFragment;
import com.example.app_jewelry.fragment.SortByDialogFragment;
import com.example.app_jewelry.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private final List<ProductResponse> productList = new ArrayList<>();
    private final apiManager apiManager = new apiManager();
    private int userId;
    private int brandId;
    private String brandName;
    private String currentSortOption = "Latest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_product);

        // Lấy userId từ SharedPreferences
        userId = new SharedPreferencesManager(this).getUserId();

        brandId = getIntent().getIntExtra("brandId", -1);
        brandName = getIntent().getStringExtra("brandName");

        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnSearch = findViewById(R.id.btnSearch);
        LinearLayout btnSort = findViewById(R.id.btnSort);
        LinearLayout btnFilter = findViewById(R.id.btnFilter);
        recyclerView = findViewById(R.id.recyclerViewProducts);

        tvTitle.setText(brandName);
        btnBack.setOnClickListener(v -> finish());
        btnSearch.setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));

        btnSort.setOnClickListener(v -> {
            SortByDialogFragment dialog = new SortByDialogFragment(option -> {
                currentSortOption = option;
                fetchProductsByBrand(currentSortOption);
            });
            dialog.show(getSupportFragmentManager(), "SortDialog");
        });

        btnFilter.setOnClickListener(v -> {
            FilterDialogFragment filterDialog = FilterDialogFragment.newInstance("brand");

            filterDialog.setFilterApplyListener((categories, brands, sizes, priceRange) -> {
                apiManager.filterProducts(
                        brands,
                        categories,
                        sizes,
                        priceRange.get(0),
                        priceRange.get(1),
                        "LowToHigh",
                        userId,
                        new Callback<List<ProductResponse>>() {
                            @Override
                            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    productAdapter.updateList(response.body());
                                } else {
                                    Toast.makeText(getApplicationContext(), "Không có sản phẩm phù hợp", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            });

            filterDialog.show(getSupportFragmentManager(), "FilterDialog");
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productAdapter = new ProductAdapter(productList, this, (product, position) -> {
            if (userId <= 0) {
                Toast.makeText(this, "Vui lòng đăng nhập để yêu thích sản phẩm", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }

            if (product.isFavorite()) {
                apiManager.removeFavorite(userId, product.getProductID(), new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        product.setFavorite(false);
                        productAdapter.notifyItemChanged(position);
                        Toast.makeText(BrandProductActivity.this, "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(BrandProductActivity.this, "Lỗi khi xóa yêu thích", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                apiManager.addFavorite(userId, product.getProductID(), new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        product.setFavorite(true);
                        productAdapter.notifyItemChanged(position);
                        Toast.makeText(BrandProductActivity.this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(BrandProductActivity.this, "Lỗi khi thêm yêu thích", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        recyclerView.setAdapter(productAdapter);

        fetchProductsByBrand(currentSortOption);
    }

    private void fetchProductsByBrand(String sortOption) {
        apiManager.getProductsByBrand(brandId, userId, sortOption, new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(BrandProductActivity.this, "Không thể tải sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                Toast.makeText(BrandProductActivity.this, "Lỗi kết nối đến server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
