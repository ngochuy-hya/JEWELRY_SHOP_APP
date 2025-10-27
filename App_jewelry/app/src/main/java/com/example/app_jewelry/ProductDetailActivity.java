package com.example.app_jewelry;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.ProductDetailResponse;
import com.example.app_jewelry.Service.DTO.request.CreateReviewRequest;
import com.example.app_jewelry.adapter.ColorAdapter;
import com.example.app_jewelry.adapter.ProductImageAdapter;
import com.example.app_jewelry.adapter.ReviewAdapter;
import com.example.app_jewelry.adapter.ReviewImagePreviewAdapter;
import com.example.app_jewelry.adapter.SizeAdapter;
import com.example.app_jewelry.entity.ProductVariant;
import com.example.app_jewelry.entity.Review;
import com.example.app_jewelry.utils.Cloudinary.CloudinaryUploader;
import com.example.app_jewelry.utils.Cloudinary.ReviewMessage;
import com.example.app_jewelry.utils.SharedPreferencesManager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private ProductDetailResponse productDetail;
    private ViewPager2 viewPagerImages;
    private TabLayout imageIndicator;
    private RecyclerView recyclerColors, recyclerSizes;
    private TextView txtProductTitle, txtRating, txtReviewCount, txtCurrentPrice, txtOriginalPrice, txtDiscount;
    private TextView txtDescription, txtReadMore, txtQuantity, txtTotalAmount;
    private TextView txtAverageRating, txtTotalReviews, txtViewAllReviews;
    private RatingBar ratingStars;
    private ImageButton btnCart;
    private Button btnEnquiry;
    private ImageButton btnWishlist, btnIncrease, btnDecrease;

    private List<ProductVariant> variantList = new ArrayList<>();
    private ColorAdapter colorAdapter;
    private SizeAdapter sizeAdapter;
    private String selectedColor = null;
    private String selectedSize = null;
    private ProductVariant selectedVariant = null;
    private String fullDescription;
    private float avgRating;
    private int reviewCount;
    private ImageButton btnBack;
    private Button btnAddToCart;
    private final int[] quantity = {1};
    private final long SLIDE_DELAY = 3000;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable slideRunnable;
    private final apiManager api = new apiManager();
    private RecyclerView recyclerSelectedImages;
    private List<ReviewMessage> selectedImages = new ArrayList<>();
    private ReviewImagePreviewAdapter previewAdapter;
    private RatingBar ratingBarReview;
    private EditText edtReviewContent;
    private ImageButton btnSelectImages;
    private Button btnSubmitReview;
    private final List<ReviewMessage> previewList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        int productId = getIntent().getIntExtra("productId", -1);
        int userId =  new SharedPreferencesManager(this).getUserId();
        btnBack.setOnClickListener(v -> {
            finish();
        });
        TextView tvCartCount = findViewById(R.id.tvCartCount);
        api.getCartItemCount(userId, new retrofit2.Callback<Integer>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Integer> call, @NonNull retrofit2.Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int count = response.body();
                    if (count > 0) {
                        tvCartCount.setText(String.valueOf(count));
                        tvCartCount.setVisibility(View.VISIBLE);
                    } else {
                        tvCartCount.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull retrofit2.Call<Integer> call, @NonNull Throwable t) {
            }
        });

        api.getProductDetail(productId, userId, new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productDetail = response.body();
                    setupProductDetail(productDetail, userId);
                }
            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                Log.e("ProductDetailAPI", "Tải chi tiết thất bại: " + t.getMessage());
            }
        });
        btnAddToCart.setOnClickListener(v -> {
            if ( userId <= 0) {
                Toast.makeText(this, "Vui lòng đăng nhập để thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }
            if (selectedVariant == null) {
                Toast.makeText(this, "Vui lòng chọn màu và kích thước", Toast.LENGTH_SHORT).show();
                return;
            }

            api.addToCart(userId, selectedVariant.getVariantID(), quantity[0], new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ProductDetailActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        // Cập nhật lại số lượng hiển thị ở icon giỏ
                        api.getCartItemCount(userId, new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    int count = response.body();
                                    TextView tvCartCount = findViewById(R.id.tvCartCount);
                                    if (count > 0) {
                                        tvCartCount.setText(String.valueOf(count));
                                        tvCartCount.setVisibility(View.VISIBLE);
                                    } else {
                                        tvCartCount.setVisibility(View.GONE);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                            }
                        });
                    } else {
                        Toast.makeText(ProductDetailActivity.this, "Thêm giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ProductDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        btnEnquiry.setOnClickListener(v -> {
            startActivity(new Intent(ProductDetailActivity.this,ChatActivity.class));
        });
        api.checkUserBoughtProduct(userId, productId, new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                    findViewById(R.id.reviewCardView).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.reviewCardView).setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                findViewById(R.id.reviewCardView).setVisibility(View.GONE);
            }
        });
        btnSelectImages.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });
        btnSubmitReview.setOnClickListener(v -> {
            String comment = edtReviewContent.getText().toString().trim();
            int rating = (int) ratingBarReview.getRating();

            if (rating == 0 || comment.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập nội dung và chọn sao", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean stillUploading = previewList.stream().anyMatch(ReviewMessage::isUploading);
            if (stillUploading) {
                Toast.makeText(this, "Vui lòng chờ upload ảnh xong", Toast.LENGTH_SHORT).show();
                return;
            }

            List<String> imageUrls = new ArrayList<>();
            for (ReviewMessage m : previewList) {
                if (m.getUploadedUrl() != null) imageUrls.add(m.getUploadedUrl());
            }
            EditText edtReviewTitle = findViewById(R.id.edtReviewTitle);
            String title = edtReviewTitle.getText().toString().trim();
            CreateReviewRequest request = new CreateReviewRequest();
            request.setUserId(userId);
            request.setProductId(productDetail.getProductId());
            request.setRating(rating);
            request.setTitle(title);
            request.setComment(comment);
            request.setImageUrls(imageUrls);

            api.submitReview(request, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ProductDetailActivity.this, "Gửi đánh giá thành công", Toast.LENGTH_SHORT).show();
                        previewList.clear();
                        previewAdapter.notifyDataSetChanged();
                        recyclerSelectedImages.setVisibility(View.GONE);
                        edtReviewContent.setText("");
                        ratingBarReview.setRating(0);
                        if (response.isSuccessful()) {
                            Toast.makeText(ProductDetailActivity.this, "Gửi đánh giá thành công", Toast.LENGTH_SHORT).show();
                            previewList.clear();
                            previewAdapter.notifyDataSetChanged();
                            recyclerSelectedImages.setVisibility(View.GONE);
                            edtReviewContent.setText("");
                            ratingBarReview.setRating(0);
                            api.getProductDetail(productDetail.getProductId(), userId, new Callback<ProductDetailResponse>() {
                                @Override
                                public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        productDetail = response.body();
                                        setupReviews(productDetail);
                                        setupRatings(productDetail);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                                    Log.e("ReloadAfterReview", "Lỗi reload chi tiết sản phẩm sau khi review: " + t.getMessage());
                                }
                            });
                        }

                    } else {
                        Toast.makeText(ProductDetailActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ProductDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


    }

    private void initViews() {
        viewPagerImages = findViewById(R.id.viewPagerImages);
        imageIndicator = findViewById(R.id.imageIndicator);
        recyclerColors = findViewById(R.id.recyclerColors);
        recyclerSizes = findViewById(R.id.recyclerSize);
        txtProductTitle = findViewById(R.id.txtProductTitle);
        txtRating = findViewById(R.id.txtRating);
        txtReviewCount = findViewById(R.id.txtReviewCount);
        txtCurrentPrice = findViewById(R.id.txtCurrentPrice);
        txtOriginalPrice = findViewById(R.id.txtOriginalPrice);
        txtDiscount = findViewById(R.id.txtDiscount);
        txtDescription = findViewById(R.id.txtDescription);
        txtReadMore = findViewById(R.id.txtReadMore);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtTotalAmount = findViewById(R.id.txtTotalAmount);
        txtAverageRating = findViewById(R.id.txtAverageRating);
        txtTotalReviews = findViewById(R.id.txtTotalReviews);
        ratingStars = findViewById(R.id.ratingStars);
        btnWishlist = findViewById(R.id.btnWishlist);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);
        txtViewAllReviews = findViewById(R.id.txtViewAllReviews);
        btnBack = findViewById(R.id.btnBack);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnCart = findViewById(R.id.btnCart);
        ratingBarReview = findViewById(R.id.ratingBarReview);
        edtReviewContent = findViewById(R.id.edtReviewContent);
        btnSelectImages = findViewById(R.id.btnSelectImages);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);
        recyclerSelectedImages = findViewById(R.id.recyclerSelectedImages);
        btnEnquiry = findViewById(R.id.btnEnquiry);

        previewAdapter = new ReviewImagePreviewAdapter(this, previewList);
        previewAdapter.setOnRemoveClickListener(position -> {
            previewList.remove(position);
            previewAdapter.notifyItemRemoved(position);
            if (previewList.isEmpty()) recyclerSelectedImages.setVisibility(View.GONE);
        });
        recyclerSelectedImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerSelectedImages.setAdapter(previewAdapter);

    }

    private void setupProductDetail(ProductDetailResponse product, int userId) {
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        txtProductTitle.setText(product.getName());
        txtRating.setText(String.format("%.1f", product.getAverageRating()));
        txtReviewCount.setText(product.getReviewCount() + " Reviews");
        txtCurrentPrice.setText(vndFormat.format(product.getPrice()));
        txtOriginalPrice.setText(vndFormat.format(product.getOriginalPrice()));
        int discountPercent = (int) Math.round(product.getDiscountPercent());
        txtDiscount.setText(String.format("(-%d%%)", discountPercent));

        List<String> imageList = new ArrayList<>(product.getAdditionalImages());
        if (product.getMainImage() != null && !product.getMainImage().isEmpty()) {
            imageList.add(0, product.getMainImage());
        }
        ProductImageAdapter adapter = new ProductImageAdapter(imageList);
        viewPagerImages.setAdapter(adapter);
        new TabLayoutMediator(imageIndicator, viewPagerImages, (tab, position) -> tab.setCustomView(R.layout.tab_dot)).attach();
        handler.postDelayed(slideRunnable = () -> {
            int currentItem = viewPagerImages.getCurrentItem();
            viewPagerImages.setCurrentItem((currentItem + 1) % adapter.getItemCount(), true);
            handler.postDelayed(slideRunnable, SLIDE_DELAY);
        }, SLIDE_DELAY);

        btnCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });
        setupWishlist(product, userId);
        setupVariants(product);
        setupQuantityControls();
        setupDescription(product);
        setupRatings(product);
        setupReviews(product);
    }

    private void setupWishlist(ProductDetailResponse product, int userId) {
        btnWishlist.setImageResource(product.isFavorite() ? R.drawable.ic_heart_fill : R.drawable.ic_heart_outline);
        btnWishlist.setOnClickListener(v -> {
            if (userId <= 0) {
                Toast.makeText(ProductDetailActivity.this, "Vui lòng đăng nhập để yêu thích sản phẩm", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }
            if (product.isFavorite()) {
                api.removeFavorite(userId, product.getProductId(), new Callback<Void>() {
                    @Override public void onResponse(Call<Void> call, Response<Void> response) {
                        product.setFavorite(false);
                        btnWishlist.setImageResource(R.drawable.ic_heart_outline);
                        Toast.makeText(ProductDetailActivity.this, "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                    }
                    @Override public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ProductDetailActivity.this, "Lỗi khi xóa yêu thích", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                api.addFavorite(userId, product.getProductId(), new Callback<Void>() {
                    @Override public void onResponse(Call<Void> call, Response<Void> response) {
                        product.setFavorite(true);
                        btnWishlist.setImageResource(R.drawable.ic_heart_fill);
                        Toast.makeText(ProductDetailActivity.this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                    }
                    @Override public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ProductDetailActivity.this, "Lỗi khi thêm yêu thích", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setupVariants(ProductDetailResponse product) {
        variantList = product.getVariants();

        Set<String> colorSet = new HashSet<>();
        List<ProductVariant> uniqueColors = new ArrayList<>();
        for (ProductVariant v : variantList) {
            if (colorSet.add(v.getColor())) uniqueColors.add(v);
        }

        colorAdapter = new ColorAdapter(this, uniqueColors);
        recyclerColors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerColors.setAdapter(colorAdapter);
        colorAdapter.setOnColorSelectedListener((variant, position) -> {
            selectedColor = variant.getColor();
            selectedSize = null;
            selectedVariant = null;
            updateSizeOptions();
            updateVariantUI();
        });

        sizeAdapter = new SizeAdapter(this, new ArrayList<>());
        recyclerSizes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerSizes.setAdapter(sizeAdapter);
        sizeAdapter.setOnSizeSelectedListener((variant, position) -> {
            selectedSize = variant.getSize();
            selectedVariant = variant;
            updateVariantUI();
        });
    }

    private void setupQuantityControls() {
        btnIncrease.setOnClickListener(v -> {
            quantity[0]++;
            txtQuantity.setText(String.valueOf(quantity[0]));
            updateTotalAmount();
        });

        btnDecrease.setOnClickListener(v -> {
            if (quantity[0] > 1) {
                quantity[0]--;
                txtQuantity.setText(String.valueOf(quantity[0]));
                updateTotalAmount();
            }
        });
    }

    private void setupDescription(ProductDetailResponse product) {
        fullDescription = product.getDescription();
        if (product.getBrandName() != null) fullDescription += "\n\nBrand: " + product.getBrandName();
        if (product.getCategoryDescription() != null) fullDescription += "\nCategory: " + product.getCategoryDescription();

        txtDescription.setText(fullDescription);
        txtDescription.setMaxLines(3);
        txtDescription.setEllipsize(TextUtils.TruncateAt.END);

        txtReadMore.setOnClickListener(v -> {
            if (txtReadMore.getText().toString().equalsIgnoreCase("Read more")) {
                txtDescription.setMaxLines(Integer.MAX_VALUE);
                txtDescription.setEllipsize(null);
                txtReadMore.setText("Collapse");
            } else {
                txtDescription.setMaxLines(3);
                txtDescription.setEllipsize(TextUtils.TruncateAt.END);
                txtReadMore.setText("Read more");
            }
        });
    }

    private void setupRatings(ProductDetailResponse product) {
        avgRating = product.getAverageRating();
        reviewCount = product.getReviewCount();
        txtAverageRating.setText(String.format("%.2f", avgRating));
        ratingStars.setRating(avgRating);
        txtTotalReviews.setText(reviewCount + " Reviews");

        Map<Integer, Integer> starStats = product.getStarCount();
        int[] starLevels = {5, 4, 3, 2, 1, 0};
        for (int star : starLevels) {
            int count = starStats.getOrDefault(star, 0);
            int percent = (reviewCount > 0) ? (int) (count * 100.0 / reviewCount) : 0;

            int resId = getResources().getIdentifier("starRow" + star, "id", getPackageName());
            View starRow = findViewById(resId);
            if (starRow != null) {
                ((TextView) starRow.findViewById(R.id.txtStarLabel)).setText(star + " ★");
                ((ProgressBar) starRow.findViewById(R.id.progressStar)).setProgress(percent);
                ((TextView) starRow.findViewById(R.id.txtStarCount)).setText(String.valueOf(count));
            }
        }
    }

    private void setupReviews(ProductDetailResponse product) {
        RecyclerView recyclerReviews = findViewById(R.id.recyclerReviews);
        List<Review> reviews = product.getReviews();
        List<Review> limitedReviews = reviews.size() > 2 ? reviews.subList(0, 2) : reviews;

        ReviewAdapter reviewAdapter = new ReviewAdapter(this, limitedReviews);
        recyclerReviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerReviews.setAdapter(reviewAdapter);

        txtViewAllReviews.setText("View All " + reviews.size() + " Reviews");
        txtViewAllReviews.setOnClickListener(v -> {
            Intent intent = new Intent(this, AllReviewsActivity.class);
            intent.putExtra("reviews", new Gson().toJson(product.getReviews()));
            intent.putExtra("avgRating", product.getAverageRating());
            intent.putExtra("reviewCount", product.getReviewCount());
            intent.putExtra("starStats", new Gson().toJson(product.getStarCount()));
            startActivity(intent);
        });
    }

    private void updateSizeOptions() {
        Log.d("DEBUG", "Selected color: " + selectedColor);
        if (selectedColor == null) return;
        Set<String> sizeSet = new HashSet<>();
        List<ProductVariant> filteredSizes = new ArrayList<>();
        for (ProductVariant v : variantList) {
            Log.d("DEBUG", "Variant color: " + v.getColor() + ", size: " + v.getSize());
            if (selectedColor.equals(v.getColor()) && sizeSet.add(v.getSize())) {
                filteredSizes.add(v);
            }
        }
        sizeAdapter.updateSizes(filteredSizes);
    }

    private void updateVariantUI() {
        if (selectedVariant == null) return;
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        txtCurrentPrice.setText(vndFormat.format(selectedVariant.getPrice()));
        txtTotalAmount.setText(vndFormat.format(selectedVariant.getPrice() * quantity[0]));

        List<String> imageList = new ArrayList<>();
        if (selectedVariant.getColor() != null && !selectedVariant.getColor().isEmpty()) {
            imageList.add(selectedVariant.getColor());
        }
        imageList.addAll(productDetail.getAdditionalImages());
        viewPagerImages.setAdapter(new ProductImageAdapter(imageList));
        new TabLayoutMediator(imageIndicator, viewPagerImages, (tab, position) -> tab.setCustomView(R.layout.tab_dot)).attach();
    }

    private void updateTotalAmount() {
        if (selectedVariant != null) {
            NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            txtTotalAmount.setText(vndFormat.format(selectedVariant.getPrice() * quantity[0]));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(slideRunnable);
    }
    private ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        ReviewMessage message = new ReviewMessage(imageUri); // trạng thái uploading
                        previewList.add(message);
                        previewAdapter.notifyItemInserted(previewList.size() - 1);
                        recyclerSelectedImages.setVisibility(View.VISIBLE);
                        CloudinaryUploader.uploadImage(this, imageUri, new CloudinaryUploader.UploadCallback() {
                            @Override
                            public void onSuccess(String imageUrl) {
                                message.setUploadedUrl(imageUrl);
                                message.setUploading(false);
                                previewAdapter.notifyItemChanged(previewList.indexOf(message));
                            }
                            @Override
                            public void onError(String errorMessage) {
                                Toast.makeText(ProductDetailActivity.this, "Upload lỗi", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

}