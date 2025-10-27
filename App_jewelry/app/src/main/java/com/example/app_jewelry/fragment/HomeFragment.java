package com.example.app_jewelry.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_jewelry.BrandProductActivity;
import com.example.app_jewelry.CategoryProductActivity;
import com.example.app_jewelry.LoginActivity;
import com.example.app_jewelry.ProductListActivity;
import com.example.app_jewelry.R;
import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.BestsellerProductResponse;
import com.example.app_jewelry.Service.DTO.reponse.ProductArrivalResponse;
import com.example.app_jewelry.Service.DTO.reponse.TodayOfferResponse;
import com.example.app_jewelry.adapter.*;
import com.example.app_jewelry.entity.Brand;
import com.example.app_jewelry.entity.Category;
import com.example.app_jewelry.entity.Slider;
import com.example.app_jewelry.utils.SharedPreferencesManager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPagerBanner;
    private TabLayout tabLayoutIndicator;
    private BannerAdapter bannerAdapter;
    private List<Slider> sliderList;

    private RecyclerView recyclerBrands, recyclerCategory;
    private BrandAdapter brandAdapter;
    private CategoryAdapter categoryAdapter;

    private RecyclerView recyclerTodayOffer, recyclerNewArrival, recyclerBestSeller;
    private TodayOfferAdapter todayOfferAdapter;
    private ProductArrivalAdapter newArrivalAdapter;
    private BestSellerProductAdapter bestSellerAdapter;

    private Handler handler = new Handler(Looper.getMainLooper());
    private final long SLIDE_DELAY = 3000;
    private Runnable slideRunnable;

    private int userId;
    private apiManager api;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        api = new apiManager();

        SharedPreferencesManager spManager = new SharedPreferencesManager(requireContext());
        userId = spManager.getUserId();

        initViews(view);
        loadBanner();
        loadCategories();
        loadBrands();
        loadTodayOffers();
        loadNewArrivals();
        loadBestSellers();

        setupViewAllClicks(view);

        return view;
    }

    private void initViews(View view) {
        viewPagerBanner = view.findViewById(R.id.viewPagerBanner);
        tabLayoutIndicator = view.findViewById(R.id.tabLayoutIndicator);

        recyclerBrands = view.findViewById(R.id.recyclerBrands);
        recyclerBrands.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerCategory = view.findViewById(R.id.recyclerCategory);
        recyclerCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerTodayOffer = view.findViewById(R.id.recyclerTodayOffer);
        recyclerTodayOffer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerNewArrival = view.findViewById(R.id.recyclerNewArrival);
        recyclerNewArrival.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerBestSeller = view.findViewById(R.id.recyclerBestSeller);
        recyclerBestSeller.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void loadBanner() {
        api.getSliders(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    sliderList = response.body();
                    bannerAdapter = new BannerAdapter(sliderList);
                    viewPagerBanner.setAdapter(bannerAdapter);
                    new TabLayoutMediator(tabLayoutIndicator, viewPagerBanner,
                            (tab, position) -> tab.setCustomView(R.layout.tab_dot)).attach();

                    setupAutoSlideBanner();
                } else {
                    Log.e("SliderAPI", "No slider data or error code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {
                Log.e("SliderAPI", "Error loading sliders: " + t.getMessage());
            }
        });
    }

    private void loadCategories() {
        api.getCategories(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<Category> categoryList = response.body();
                    categoryAdapter = new CategoryAdapter(categoryList, category -> {
                        Intent intent = new Intent(requireContext(), CategoryProductActivity.class);
                        intent.putExtra("categoryId", category.getCategoryID());
                        intent.putExtra("categoryName", category.getName());
                        startActivity(intent);
                    });
                    recyclerCategory.setAdapter(categoryAdapter);
                    recyclerCategory.setVisibility(View.VISIBLE);
                    requireView().findViewById(R.id.textTitleCategory).setVisibility(View.VISIBLE);
                } else {
                    recyclerCategory.setVisibility(View.GONE);
                    requireView().findViewById(R.id.textTitleCategory).setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                recyclerCategory.setVisibility(View.GONE);
                requireView().findViewById(R.id.textTitleCategory).setVisibility(View.GONE);
                Log.e("CategoryAPI", "Error loading categories: " + t.getMessage());
            }
        });
    }

    private void loadBrands() {
        api.getBrands(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<Brand> brandList = response.body();
                    brandAdapter = new BrandAdapter(brandList, brand -> {
                        Intent intent = new Intent(requireContext(), BrandProductActivity.class);
                        intent.putExtra("brandId", brand.getBrandID());
                        intent.putExtra("brandName", brand.getName());
                        startActivity(intent);
                    });
                    recyclerBrands.setAdapter(brandAdapter);
                    recyclerBrands.setVisibility(View.VISIBLE);
                    requireView().findViewById(R.id.textBrandTitle).setVisibility(View.VISIBLE);
                } else {
                    recyclerBrands.setVisibility(View.GONE);
                    requireView().findViewById(R.id.textBrandTitle).setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                recyclerBrands.setVisibility(View.GONE);
                requireView().findViewById(R.id.textBrandTitle).setVisibility(View.GONE);
                Log.e("BrandAPI", "Error loading brands: " + t.getMessage());
            }
        });
    }

    private void loadTodayOffers() {
        api.getTodayOffers(new Callback<List<TodayOfferResponse>>() {
            @Override
            public void onResponse(Call<List<TodayOfferResponse>> call, Response<List<TodayOfferResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<TodayOfferResponse> todayOfferList = response.body();
                    todayOfferAdapter = new TodayOfferAdapter(requireContext(), todayOfferList, product -> {
                        handleFavoriteAction(product.getProductId(), true, "today_offer");
                    });
                    recyclerTodayOffer.setAdapter(todayOfferAdapter);
                    recyclerTodayOffer.setVisibility(View.VISIBLE);
                    requireView().findViewById(R.id.textTodayOfferTitle).setVisibility(View.VISIBLE);
                } else {
                    recyclerTodayOffer.setVisibility(View.GONE);
                    requireView().findViewById(R.id.textTodayOfferTitle).setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<TodayOfferResponse>> call, Throwable t) {
                recyclerTodayOffer.setVisibility(View.GONE);
                requireView().findViewById(R.id.textTodayOfferTitle).setVisibility(View.GONE);
                Log.e("TodayOfferAPI", "Error loading today offers: " + t.getMessage());
            }
        });
    }

    private void loadNewArrivals() {
        api.getNewArrivalProducts(userId, new Callback<List<ProductArrivalResponse>>() {
            @Override
            public void onResponse(Call<List<ProductArrivalResponse>> call, Response<List<ProductArrivalResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<ProductArrivalResponse> newArrivalList = response.body();
                    newArrivalAdapter = new ProductArrivalAdapter(requireContext(), newArrivalList, (product, isFavorite) -> {
                        handleFavoriteAction(product.getProductId(), isFavorite, "new_arrival");
                    });
                    recyclerNewArrival.setAdapter(newArrivalAdapter);
                    recyclerNewArrival.setVisibility(View.VISIBLE);
                    requireView().findViewById(R.id.textNewArrivalTitle).setVisibility(View.VISIBLE);
                } else {
                    recyclerNewArrival.setVisibility(View.GONE);
                    requireView().findViewById(R.id.textNewArrivalTitle).setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<ProductArrivalResponse>> call, Throwable t) {
                recyclerNewArrival.setVisibility(View.GONE);
                requireView().findViewById(R.id.textNewArrivalTitle).setVisibility(View.GONE);
                Log.e("NewArrivalAPI", "Error loading new arrivals: " + t.getMessage());
            }
        });
    }

    private void loadBestSellers() {
        api.getBestSellerProducts(userId, new Callback<List<BestsellerProductResponse>>() {
            @Override
            public void onResponse(Call<List<BestsellerProductResponse>> call, Response<List<BestsellerProductResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<BestsellerProductResponse> bestSellers = response.body();
                    bestSellerAdapter = new BestSellerProductAdapter(requireContext(), bestSellers, (product, isFavorite) -> {
                        handleFavoriteAction(product.getProductId(), isFavorite, "bestseller");
                    });
                    recyclerBestSeller.setAdapter(bestSellerAdapter);
                    recyclerBestSeller.setVisibility(View.VISIBLE);
                    requireView().findViewById(R.id.textBestSellerTitle).setVisibility(View.VISIBLE);
                } else {
                    recyclerBestSeller.setVisibility(View.GONE);
                    requireView().findViewById(R.id.textBestSellerTitle).setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<BestsellerProductResponse>> call, Throwable t) {
                recyclerBestSeller.setVisibility(View.GONE);
                requireView().findViewById(R.id.textBestSellerTitle).setVisibility(View.GONE);
                Log.e("BestSellerAPI", "Error loading best sellers: " + t.getMessage());
            }
        });
    }

    private Callback<Void> simpleCallback(String successMsg) {
        return new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("Favorite", successMsg);
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Favorite", "Favorite action failed", t);
            }
        };
    }

    private void handleFavoriteAction(int productId, boolean isFavorite, String sourceTag) {
        if (userId == -1 || userId == 0) {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
            return;
        }

        if (isFavorite) {
            api.addFavorite(userId, productId, simpleCallback("Added favorite (" + sourceTag + ")"));
        } else {
            api.removeFavorite(userId, productId, simpleCallback("Removed favorite (" + sourceTag + ")"));
        }
    }

    private void setupAutoSlideBanner() {
        slideRunnable = () -> {
            int currentItem = viewPagerBanner.getCurrentItem();
            int nextItem = (currentItem + 1) % bannerAdapter.getItemCount();
            viewPagerBanner.setCurrentItem(nextItem, true);
            handler.postDelayed(slideRunnable, SLIDE_DELAY);
        };
        handler.postDelayed(slideRunnable, SLIDE_DELAY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(slideRunnable);
    }

    private void setupViewAllClicks(View view) {
        view.findViewById(R.id.viewAllTodayOffer).setOnClickListener(v ->
                openProductList("today_offer", "Today Offer"));
        view.findViewById(R.id.viewAllNewArrival).setOnClickListener(v ->
                openProductList("new_arrival", "New Arrival"));
        view.findViewById(R.id.viewAllBestSeller).setOnClickListener(v ->
                openProductList("best_seller", "Best Seller"));
    }

    private void openProductList(String type, String title) {
        Intent intent = new Intent(requireContext(), ProductListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}
