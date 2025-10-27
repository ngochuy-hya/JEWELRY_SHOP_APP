package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.ResultSearchResponse;
import com.example.app_jewelry.adapter.ProductSearchAdapter;
import com.example.app_jewelry.adapter.RecentSearchAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearch;
    private ImageView btnBack, btnClearSearch;
    private TextView tvNoResults;
    private RecyclerView recyclerView;

    private List<String> recentSearches = new ArrayList<>();

    private ProductSearchAdapter productSearchAdapter;
    private RecentSearchAdapter recentSearchAdapter;

    private apiManager apiManager; // dùng apiManager bạn đã viết

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(0, insets.getInsets(WindowInsetsCompat.Type.systemBars()).top, 0, 0);
            return insets;
        });

        apiManager = new apiManager(); // khởi tạo apiManager

        mappingViews();
        setupListeners();
        showRecentSearches();
    }

    private void mappingViews() {
        etSearch = findViewById(R.id.etSearch);
        btnBack = findViewById(R.id.btnBack);
        btnClearSearch = findViewById(R.id.btnClearSearch);
        recyclerView = findViewById(R.id.recyclerView);
        tvNoResults = findViewById(R.id.tvNoResults);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnClearSearch.setOnClickListener(v -> {
            etSearch.setText("");
            showRecentSearches();
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                if (query.isEmpty()) {
                    showRecentSearches();
                } else {
                    searchProducts(query);
                }
            }
        });
    }

    private void showRecentSearches() {
        tvNoResults.setVisibility(View.GONE);

        recentSearchAdapter = new RecentSearchAdapter(recentSearches,
                query -> {
                    etSearch.setText(query);
                    etSearch.setSelection(query.length());
                    searchProducts(query);
                },
                v -> {
                    recentSearches.clear();
                    showRecentSearches();
                }
        );

        recyclerView.setAdapter(recentSearchAdapter);
    }

    private void searchProducts(String query) {
        tvNoResults.setVisibility(View.GONE);

        apiManager.searchProducts(query, new Callback<List<ResultSearchResponse>>() {
            @Override
            public void onResponse(Call<List<ResultSearchResponse>> call, Response<List<ResultSearchResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ResultSearchResponse> results = response.body();

                    if (results.isEmpty()) {
                        tvNoResults.setVisibility(View.VISIBLE);
                    } else {
                        tvNoResults.setVisibility(View.GONE);
                    }

                    productSearchAdapter = new ProductSearchAdapter(SearchActivity.this, results, product -> {
                        String clickedName = product.getName();
                        if (!recentSearches.contains(clickedName)) {
                            recentSearches.add(0, clickedName);
                        }

                        Intent intent = new Intent(SearchActivity.this, ProductDetailActivity.class);
                        intent.putExtra("productId", product.getProductID());
                        startActivity(intent);
                    });


                    recyclerView.setAdapter(productSearchAdapter);
                } else {
                    tvNoResults.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<ResultSearchResponse>> call, Throwable t) {
                tvNoResults.setVisibility(View.VISIBLE);
                Log.e("SearchAPI", "Lỗi API: " + t.getMessage());
            }
        });
    }
}
