package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterOTPActivity extends AppCompatActivity {

    private EditText otpEditText;
    private Button verifyButton;
    private apiManager apiManager;
    private String email;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enter_otpactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });
        otpEditText = findViewById(R.id.otpEditText);
        verifyButton = findViewById(R.id.confirmButton);
        apiManager = new apiManager();

        // Lấy email truyền từ SignUpActivity hoặc ResetPasswordActivity
        email = getIntent().getStringExtra("email");

        verifyButton.setOnClickListener(v -> {
            String otp = otpEditText.getText().toString().trim();

            if (otp.isEmpty()) {
                otpEditText.setError("Please enter OTP");
                return;
            }

            // Gọi API verify OTP
            apiManager.verifyOtp(email, otp, new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(EnterOTPActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EnterOTPActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(EnterOTPActivity.this, "Verification failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Toast.makeText(EnterOTPActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
