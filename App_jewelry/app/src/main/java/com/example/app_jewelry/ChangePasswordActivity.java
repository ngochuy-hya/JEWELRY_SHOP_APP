package com.example.app_jewelry;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.app_jewelry.Service.DTO.request.ChangePasswordRequest;
import com.example.app_jewelry.utils.SharedPreferencesManager;
import com.example.app_jewelry.utils.InputValidator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText edtOldPassword, edtNewPassword, edtConfirmPassword;
    private Button btnUpdatePassword;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        btnBack = findViewById(R.id.btnBack);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnUpdatePassword = findViewById(R.id.btnUpdatePassword);

        // Quay lại
        btnBack.setOnClickListener(v -> finish());

        // Lấy userId từ SharedPreferences
        SharedPreferencesManager prefManager = new SharedPreferencesManager(this);
        int userId = prefManager.getUserId();

        // Sự kiện cập nhật mật khẩu
        btnUpdatePassword.setOnClickListener(v -> {
            String oldPass = edtOldPassword.getText().toString().trim();
            String newPass = edtNewPassword.getText().toString().trim();
            String confirmPass = edtConfirmPassword.getText().toString().trim();

            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!InputValidator.isValidPassword(oldPass)) {
                edtOldPassword.setError("Invalid old password format");
                return;
            }

            if (!InputValidator.isStrongPassword(newPass)) {
                edtNewPassword.setError("Password must be at least 8 characters, include uppercase and digit");
                return;
            }

            if (!newPass.equals(confirmPass)) {
                edtConfirmPassword.setError("Passwords do not match");
                return;
            }

            ChangePasswordRequest request = new ChangePasswordRequest();
            request.setOldPassword(oldPass);
            request.setNewPassword(newPass);

            new apiManager().changePassword(userId, request, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ChangePasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                        finish(); // quay lại sau khi đổi xong
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Old password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("ChangePassword", "Error: " + t.getMessage());
                    Toast.makeText(ChangePasswordActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
