package com.example.app_jewelry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.CartItemResponse;
import com.example.app_jewelry.Service.DTO.reponse.PayOSResponse;
import com.example.app_jewelry.Service.DTO.reponse.PaymentStatusResponse;
import com.example.app_jewelry.Service.DTO.reponse.VoucherResponse;
import com.example.app_jewelry.Service.DTO.request.CreateOrderRequest;
import com.example.app_jewelry.Service.DTO.request.PayOSRequest;
import com.example.app_jewelry.entity.Address;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentQRActivity extends AppCompatActivity {

    private TextView tvQrExpiryTime;
    private Button btnBack;
    private ImageView imgQrCode;

    private double amount;
    private List<CartItemResponse> selectedItems;
    private Address selectedAddress;
    private VoucherResponse appliedVoucher;

    private String paymentUrl;
    private final apiManager api = new apiManager();
    private static final long COUNTDOWN_TIME_MS = 5 * 60 * 1000; // 5 phút
    private static final long POLLING_INTERVAL_MS = 5 * 1000; // 5 giây

    private String orderCode = null;

    private Handler handler = new Handler();
    private Runnable paymentStatusChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_qractivity);

        btnBack = findViewById(R.id.btnBack);
        imgQrCode = findViewById(R.id.imgQrCode);
        tvQrExpiryTime = findViewById(R.id.tvQrExpiryTime);
        TextView tvAmount = findViewById(R.id.tvAmount);


        amount = getIntent().getDoubleExtra("amount", 0);
        tvAmount.setText("Amount: ₫" + String.format("%,.0f", amount));
        selectedItems = (List<CartItemResponse>) getIntent().getSerializableExtra("selectedItems");
        selectedAddress = (Address) getIntent().getSerializableExtra("selectedAddress");
        appliedVoucher = (VoucherResponse) getIntent().getSerializableExtra("appliedVoucher");

        btnBack.setOnClickListener(v -> {
            stopPolling();
            setResult(RESULT_CANCELED);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startQrCountdown();
        callCreatePaymentApi();
    }

    private void callCreatePaymentApi() {
        PayOSRequest request = new PayOSRequest();
        request.setAmount((int) amount);
        request.setDescription("Thanh toán đơn hàng");
        request.setReturnUrl("https://your-return-url.com");
        request.setCancelUrl("https://your-cancel-url.com");

        api.createPayment(request, new Callback<PayOSResponse>() {
            @Override
            public void onResponse(Call<PayOSResponse> call, Response<PayOSResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getError() == 0) {
                    paymentUrl = response.body().getData().get("checkoutUrl").getAsString();
                    orderCode = response.body().getData().get("orderCode").getAsString(); // hoặc getAsLong() tuỳ kiểu
                    generateQRCode(paymentUrl);
                    startPolling();
                } else {
                    Toast.makeText(PaymentQRActivity.this, "Không lấy được đường link thanh toán", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<PayOSResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PaymentQRActivity.this, "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void generateQRCode(String text) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 400, 400);
            imgQrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Tạo mã QR thất bại", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private void startQrCountdown() {
        new CountDownTimer(COUNTDOWN_TIME_MS, 1000) {
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String timeFormatted = String.format("QR code is valid for %02d:%02d", minutes, seconds);
                tvQrExpiryTime.setText(timeFormatted);
            }

            public void onFinish() {
                tvQrExpiryTime.setText("QR code has expired");
                stopPolling();
                setResult(RESULT_CANCELED);
                finish();
            }
        }.start();
    }

    private void startPolling() {
        paymentStatusChecker = new Runnable() {
            @Override
            public void run() {
                checkPaymentStatus();
                handler.postDelayed(this, POLLING_INTERVAL_MS);
            }
        };
        handler.post(paymentStatusChecker);
    }

    private void stopPolling() {
        if (paymentStatusChecker != null) {
            handler.removeCallbacks(paymentStatusChecker);
        }
    }

    private void checkPaymentStatus() {
        if (orderCode == null) return;

        long orderCodeLong;
        try {
            orderCodeLong = Long.parseLong(orderCode);
            Log.d("PaymentQRActivity", "orderCodeLong = " + orderCodeLong);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        api.getPaymentStatus(orderCodeLong, new Callback<PaymentStatusResponse>() {
            @Override
            public void onResponse(Call<PaymentStatusResponse> call, Response<PaymentStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String status = response.body().getStatus();
                    if ("PAID".equalsIgnoreCase(status)) {
                        stopPolling();

                        CreateOrderRequest orderRequest = new CreateOrderRequest();
                        orderRequest.setUserId(selectedAddress.getUserId()); // hoặc userId bạn lấy từ đâu đó
                        orderRequest.setAddressId(selectedAddress.getAddressId());
                        if (appliedVoucher != null) {
                            orderRequest.setVoucherCode(appliedVoucher.getCode());
                        }
                        List<CreateOrderRequest.OrderItemRequest> items = new ArrayList<>();
                        for (CartItemResponse item : selectedItems) {
                            CreateOrderRequest.OrderItemRequest oi = new CreateOrderRequest.OrderItemRequest();
                            oi.setVariantId(item.getVariantId());
                            oi.setQuantity(item.getQuantity());
                            items.add(oi);
                        }
                        orderRequest.setItems(items);

                        api.createOrder(orderRequest, new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    // Lưu đơn thành công, chuyển sang màn hình thành công
                                    clearPaidCartItems();
                                    Intent intent = new Intent(PaymentQRActivity.this, OrderSuccessActivity.class);
                                    intent.putExtra("selectedAddress", selectedAddress); // truyền Address
                                    intent.putExtra("orderedItems", new ArrayList<>(selectedItems)); // truyền danh sách sản phẩm nếu cần
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(PaymentQRActivity.this, "Lỗi lưu đơn hàng", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(PaymentQRActivity.this, "Lỗi mạng khi lưu đơn hàng", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if ("FAILED".equalsIgnoreCase(status)) {
                        stopPolling();
                        Toast.makeText(PaymentQRActivity.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_CANCELED);
                        finish();
                    } else {
                        // Pending hoặc trạng thái khác, báo user chờ
                    }
                } else {
                    Toast.makeText(PaymentQRActivity.this, "Không lấy được trạng thái thanh toán", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentStatusResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PaymentQRActivity.this, "Lỗi mạng khi kiểm tra thanh toán", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void clearPaidCartItems() {
        int userId = selectedAddress.getUserId();

        for (CartItemResponse item : selectedItems) {
            int cartItemId = item.getCartItemId();
            api.deleteCartItem(userId, cartItemId, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

}
