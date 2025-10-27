package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.Service.DTO.reponse.CartItemResponse;
import com.example.app_jewelry.Service.DTO.reponse.VoucherResponse;
import com.example.app_jewelry.adapter.CartAdapter;
import com.example.app_jewelry.adapter.CheckoutProductAdapter;
import com.example.app_jewelry.entity.Address;

import java.io.Serializable;
import java.util.List;

public class CheckoutPaymentActivity extends AppCompatActivity {

    private Button btnPlaceOrder;
    private TextView tvReceiverName, tvPhone, tvFullAddress;
    private TextView tvTotalAmount, tvOfferDiscount, tvTotalMRP;
    private RecyclerView recyclerOrderItems;

    private List<CartItemResponse> selectedItems;
    private Address selectedAddress;
    private VoucherResponse appliedVoucher;
    private LinearLayout layoutPaymentFailed;
    private ImageView btnBack;

    private double totalMRP = 0;
    private double totalAmount = 0;
    private double discount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout_payment);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        tvReceiverName = findViewById(R.id.tvReceiverName);
        tvPhone = findViewById(R.id.tvPhone);
        tvFullAddress = findViewById(R.id.tvFullAddress);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvOfferDiscount = findViewById(R.id.tvOfferDiscount);
        tvTotalMRP = findViewById(R.id.tvTotalMRP);
        recyclerOrderItems = findViewById(R.id.recyclerOrderItems);
        layoutPaymentFailed = findViewById(R.id.layoutPaymentFailed);
        layoutPaymentFailed.setVisibility(View.GONE);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });

        // Nhận dữ liệu
        selectedItems = (List<CartItemResponse>) getIntent().getSerializableExtra("selectedItems");
        selectedAddress = (Address) getIntent().getSerializableExtra("selectedAddress");
        appliedVoucher = (VoucherResponse) getIntent().getSerializableExtra("appliedVoucher");

        if (selectedAddress != null) {
            tvReceiverName.setText("Name: " + selectedAddress.getReceiverName());
            tvPhone.setText("Phone: " + selectedAddress.getPhone());
            String fullAddr = selectedAddress.getAddressLine() + ", " +
                    selectedAddress.getArea() + ", " +
                    selectedAddress.getCity() + ", " +
                    selectedAddress.getState();
            tvFullAddress.setText("Address: " + fullAddr);
        }

        CheckoutProductAdapter adapter = new CheckoutProductAdapter(this, selectedItems);
        recyclerOrderItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrderItems.setAdapter(adapter);


        calculateTotal();

        btnPlaceOrder.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaymentQRActivity.class);
            intent.putExtra("amount", totalAmount);
            intent.putExtra("selectedItems", (Serializable) selectedItems);
            intent.putExtra("selectedAddress", selectedAddress);
            intent.putExtra("appliedVoucher", appliedVoucher);
            intent.putExtra("expiryMillis", System.currentTimeMillis() + (5 * 60 * 1000));
            startActivityForResult(intent, 1001);
        });

    }

    private void calculateTotal() {
        totalMRP = 0;
        for (CartItemResponse item : selectedItems) {
            totalMRP += item.getPrice() * item.getQuantity();
        }

        discount = 0;
        if (appliedVoucher != null) {
            if ("percent".equalsIgnoreCase(appliedVoucher.getDiscountType())) {
                discount = totalMRP * (appliedVoucher.getDiscountValue() / 100.0);
                if (appliedVoucher.getMaxDiscountValue() != null && discount > appliedVoucher.getMaxDiscountValue()) {
                    discount = appliedVoucher.getMaxDiscountValue();
                }
            } else {
                discount = appliedVoucher.getDiscountValue();
            }
        }

        totalAmount = totalMRP - discount;

        tvTotalMRP.setText("Total MRP: ₫" + String.format("%,.0f", totalMRP));
        tvOfferDiscount.setText("Offer Discount: -₫" + String.format("%,.0f", discount));
        tvTotalAmount.setText("Total: ₫" + String.format("%,.0f", totalAmount));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            if (resultCode == RESULT_CANCELED) {
                layoutPaymentFailed.setVisibility(View.VISIBLE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
