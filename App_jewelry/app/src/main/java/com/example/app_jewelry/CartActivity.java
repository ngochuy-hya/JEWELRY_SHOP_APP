package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.CartItemResponse;
import com.example.app_jewelry.Service.DTO.reponse.VoucherResponse;
import com.example.app_jewelry.adapter.CartAdapter;
import com.example.app_jewelry.utils.SharedPreferencesManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private Button btnCheckout;
    private ImageView btnBack;
    private EditText edtVoucherCode;
    private Button btnApplyVoucher;
    private TextView tvAppliedVoucher;
    private TextView tvVoucherError;
    private TextView tvTotalAmount, tvOfferDiscount, tvTotalMRP;
    private TextView txtCheckVoucher;

    private final apiManager api = new apiManager();
    private int userId = -1;

    private List<VoucherResponse> availableVouchers = new ArrayList<>();
    private List<CartItemResponse> cartItemList = new ArrayList<>();
    private VoucherResponse appliedVoucher = null;

    private double cartTotal = 0;
    private double totalMRP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Lấy userId từ SharedPreferences
        userId = new SharedPreferencesManager(this).getUserId();
        if (userId == -1 || userId == 0) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Ánh xạ
        recyclerView = findViewById(R.id.recyclerCartItems);
        btnCheckout = findViewById(R.id.btnProceedToCheckout);
        btnBack = findViewById(R.id.btnBack);
        txtCheckVoucher = findViewById(R.id.txtCheckVoucher);
        edtVoucherCode = findViewById(R.id.edtVoucherCode);
        btnApplyVoucher = findViewById(R.id.btnApplyVoucher);
        tvAppliedVoucher = findViewById(R.id.tvAppliedVoucher);
        tvVoucherError = findViewById(R.id.tvVoucherError);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvOfferDiscount = findViewById(R.id.tvOfferDiscount);
        tvTotalMRP = findViewById(R.id.tvTotalMRP);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnBack.setOnClickListener(v -> finish());
        txtCheckVoucher.setOnClickListener(v -> startActivity(new Intent(this, CheckVoucherActivity.class)));
        btnApplyVoucher.setOnClickListener(v -> applyVoucher());

        btnCheckout.setOnClickListener(v -> {
            List<CartItemResponse> selectedItems = getSelectedCartItems();
            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất 1 sản phẩm để thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(CartActivity.this, FormAddressActivity.class);
            intent.putExtra("selectedItems", new ArrayList<>(selectedItems));
            intent.putExtra("appliedVoucher", appliedVoucher);
            startActivity(intent);
        });

        fetchAvailableVouchers();
        loadCartItems();
    }

    private void fetchAvailableVouchers() {
        api.getAvailableVouchers(userId, new Callback<List<VoucherResponse>>() {
            @Override
            public void onResponse(Call<List<VoucherResponse>> call, Response<List<VoucherResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    availableVouchers.clear();
                    availableVouchers.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<VoucherResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadCartItems() {
        api.getCartItemsByUserId(userId, new Callback<List<CartItemResponse>>() {
            @Override
            public void onResponse(Call<List<CartItemResponse>> call, Response<List<CartItemResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cartItemList.clear();
                    cartItemList.addAll(response.body());

                    cartAdapter = new CartAdapter(CartActivity.this, cartItemList, new CartAdapter.CartItemListener() {
                        @Override
                        public void onQuantityChanged(CartItemResponse item, int newQuantity) {
                            if (newQuantity + 1 > item.getStock()) {
                                Toast.makeText(CartActivity.this, "Không đủ hàng trong kho. Tồn kho: " + item.getStock(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            api.updateCartItemQuantity(userId, item.getCartItemId(), newQuantity, new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        item.setQuantity(newQuantity);
                                        calculateTotal();
                                    } else {
                                        Toast.makeText(CartActivity.this, "Lỗi khi cập nhật số lượng", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(CartActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onItemRemoved(CartItemResponse item) {
                            api.deleteCartItem(userId, item.getCartItemId(), new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        cartAdapter.removeItem(item);
                                        cartItemList.remove(item);
                                        calculateTotal();
                                    } else {
                                        Toast.makeText(CartActivity.this, "Lỗi khi xoá sản phẩm", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(CartActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onSelectionChanged() {
                            calculateTotal();
                        }
                    });

                    recyclerView.setAdapter(cartAdapter);
                    calculateTotal();
                } else {
                    Toast.makeText(CartActivity.this, "Không có dữ liệu giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItemResponse>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Không thể tải giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyVoucher() {
        String inputCode = edtVoucherCode.getText().toString().trim();

        VoucherResponse matched = null;
        for (VoucherResponse vr : availableVouchers) {
            if (vr.getCode().equalsIgnoreCase(inputCode)) {
                matched = vr;
                break;
            }
        }

        if (matched == null) {
            tvVoucherError.setText("Voucher không tồn tại");
            tvVoucherError.setVisibility(View.VISIBLE);
            tvAppliedVoucher.setVisibility(View.GONE);
            appliedVoucher = null;
            calculateTotal();
            return;
        }

        if (!isVoucherApplicable(matched)) {
            tvVoucherError.setText("Voucher không đủ điều kiện áp dụng");
            tvVoucherError.setVisibility(View.VISIBLE);
            tvAppliedVoucher.setVisibility(View.GONE);
            appliedVoucher = null;
            calculateTotal();
            return;
        }

        appliedVoucher = matched;
        String text = "Applied: " + matched.getCode();
        if ("percent".equalsIgnoreCase(matched.getDiscountType())) {
            text += " - " + matched.getDiscountValue() + "%";
        } else {
            text += " - ₫" + String.format("%,.0f", matched.getDiscountValue());
        }

        tvAppliedVoucher.setText(text);
        tvAppliedVoucher.setVisibility(View.VISIBLE);
        tvVoucherError.setVisibility(View.GONE);
        calculateTotal();
    }

    private boolean isVoucherApplicable(VoucherResponse vr) {
        List<CartItemResponse> selectedItems = cartAdapter.getSelectedItems();
        double subtotal = 0;
        for (CartItemResponse item : selectedItems) {
            subtotal += item.getPrice() * item.getQuantity();
        }

        if (vr.getMinOrderAmount() > 0 && subtotal < vr.getMinOrderAmount()) {
            return false;
        }

        if (vr.getExpiryDate() != null && !vr.getExpiryDate().isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date expiry = sdf.parse(vr.getExpiryDate());
                if (expiry != null && new Date().after(expiry)) {
                    return false;
                }
            } catch (ParseException e) {
                return false;
            }
        }

        return true;
    }

    private void calculateTotal() {
        List<CartItemResponse> selectedItems = cartAdapter.getSelectedItems();

        cartTotal = 0;
        totalMRP = 0;
        for (CartItemResponse item : selectedItems) {
            double itemTotal = item.getPrice() * item.getQuantity();
            totalMRP += itemTotal;
        }

        double discount = 0;
        if (appliedVoucher != null && isVoucherApplicable(appliedVoucher)) {
            if ("percent".equalsIgnoreCase(appliedVoucher.getDiscountType())) {
                discount = totalMRP * (appliedVoucher.getDiscountValue() / 100.0);
                if (appliedVoucher.getMaxDiscountValue() != null && discount > appliedVoucher.getMaxDiscountValue()) {
                    discount = appliedVoucher.getMaxDiscountValue();
                }
            } else {
                discount = appliedVoucher.getDiscountValue();
            }
        }

        cartTotal = totalMRP - discount;

        tvTotalAmount.setText("Total: ₫" + String.format("%,.0f", cartTotal));
        tvTotalMRP.setText("Total MRP: ₫" + String.format("%,.0f", totalMRP));
        tvOfferDiscount.setText("Offer Discount: -₫" + String.format("%,.0f", discount));
    }

    private List<CartItemResponse> getSelectedCartItems() {
        return cartAdapter != null ? cartAdapter.getSelectedItems() : new ArrayList<>();
    }
}
