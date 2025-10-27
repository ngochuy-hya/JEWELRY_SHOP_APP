package com.example.app_jewelry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.UserResponse;
import com.example.app_jewelry.Service.DTO.request.UpdateUserRequest;
import com.example.app_jewelry.utils.SharedPreferencesManager;
import com.example.app_jewelry.utils.InputValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {
    private EditText edtName, edtEmail, edtPhone;
    private TextView avatarText;
    private Button btnUpdate, btnChangePassword;
    private ImageView btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        btnback = findViewById(R.id.btnBack);
        btnback.setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        avatarText = findViewById(R.id.avatarText);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        SharedPreferencesManager prefManager = new SharedPreferencesManager(this);
        int userId = prefManager.getUserId();
        btnChangePassword.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this,ChangePasswordActivity.class));
        });
        apiManager apiManager = new apiManager();
        btnUpdate.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();

            // Validate nếu cần
            if (name.isEmpty()) {
                edtName.setError("Vui lòng nhập tên");
                return;
            }

            if (!InputValidator.isValidName(name)) {
                edtName.setError("Tên không hợp lệ. Tối thiểu 2 ký tự chữ.");
                return;
            }

            if (!phone.isEmpty() && !InputValidator.isValidPhone(phone)) {
                edtPhone.setError("Số điện thoại không hợp lệ (10 chữ số, bắt đầu bằng 0).");
                return;
            }

            // Tạo request
            UpdateUserRequest request = new UpdateUserRequest();
            request.setFullName(name);
            request.setPhone(phone);

            // Gọi API cập nhật
            apiManager.updateUser(userId, request, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("Profile", "Cập nhật thành công");
                        // Có thể hiển thị Toast:
                        Toast.makeText(ProfileActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Profile", "Cập nhật thất bại: " + response.code());
                        Toast.makeText(ProfileActivity.this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("Profile", "Lỗi kết nối khi cập nhật: " + t.getMessage());
                    Toast.makeText(ProfileActivity.this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
                }
            });
        });

        apiManager.getUserById(userId, new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse user = response.body();
                    edtName.setText(user.getFullName());
                    edtEmail.setText(user.getEmail());
                    edtPhone.setText(user.getPhone());

                    // Set chữ avatar là ký tự đầu tiên
                    if (user.getFullName() != null && !user.getFullName().isEmpty()) {
                        avatarText.setText(user.getFullName().substring(0, 1).toUpperCase());
                    }
                } else {
                    Log.e("Profile", "Lỗi khi load user: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("Profile", "Lỗi kết nối: " + t.getMessage());
            }
        });
    }

}