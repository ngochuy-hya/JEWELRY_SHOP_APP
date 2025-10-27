package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ProductListActivity extends AppCompatActivity {

    private TextView tvTitle;
    private ImageView btnBack, btnSearch;
    private LinearLayout btnSort, btnFilter;
    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;
    private List<ProductResponse> productList;
    private String currentSortOption = "Latest";

    private final apiManager apiManager = new apiManager();
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        userId = new SharedPreferencesManager(this).getUserId();

        String title = getIntent().getStringExtra("title");
        String type = getIntent().getStringExtra("type");
        if (title == null) title = "Products";

        tvTitle = findViewById(R.id.tvTitle);
        btnBack = findViewById(R.id.btnBack);
        btnSearch = findViewById(R.id.btnSearch);
        btnSort = findViewById(R.id.btnSort);
        btnFilter = findViewById(R.id.btnFilter);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);

        tvTitle.setText(title);
        btnBack.setOnClickListener(v -> finish());
        btnSearch.setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));

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

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, this, (product, position) -> {
            if (userId == null || userId <= 0) {
                Toast.makeText(ProductListActivity.this, "Vui lòng đăng nhập để yêu thích sản phẩm", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }

            if (product.isFavorite()) {
                apiManager.removeFavorite(userId, product.getProductID(), new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        product.setFavorite(false);
                        productAdapter.notifyItemChanged(position);
                        Toast.makeText(ProductListActivity.this, "Đã bỏ yêu thích", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ProductListActivity.this, "Lỗi khi bỏ yêu thích", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                apiManager.addFavorite(userId, product.getProductID(), new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        product.setFavorite(true);
                        productAdapter.notifyItemChanged(position);
                        Toast.makeText(ProductListActivity.this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ProductListActivity.this, "Lỗi khi thêm yêu thích", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        recyclerViewProducts.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewProducts.setAdapter(productAdapter);

        btnSort.setOnClickListener(v -> {
            SortByDialogFragment dialog = new SortByDialogFragment(option -> {
                currentSortOption = option;
                loadProductsFromApi(getIntent().getStringExtra("type"), userId);
            });
            dialog.show(getSupportFragmentManager(), "SortDialog");
        });

        Log.d("ProductListActivity", "Resolved UserID: " + userId);
        loadProductsFromApi(type, userId != null ? userId : -1);
    }

    private void loadProductsFromApi(String type, int resolvedUserId) {
        apiManager.getProductsByType(type, resolvedUserId, currentSortOption, new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ProductListActivity.this, "Không thể tải sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                Toast.makeText(ProductListActivity.this, "Lỗi khi tải sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
