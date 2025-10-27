package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.UserResponse;
import com.example.app_jewelry.utils.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Response;

public class UserAccountActivity extends AppCompatActivity {

    private ImageView btnBack;
    private View itemProfile, itemAddress, itemwishlist, itemOrder;
    private Button btnLogout;
    private TextView tvClickHere;
    private TextView tvUsername, avatarText;


    private int userId = -1;
    private SharedPreferencesManager spManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_account);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spManager = new SharedPreferencesManager(this);
        userId = spManager.getUserId();

        // Nếu chưa đăng nhập thì chuyển sang LoginActivity
        if (userId == -1 || spManager.getToken() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish(); // không cho vào nếu chưa đăng nhập
            return;
        }

        // Ánh xạ view
        btnBack = findViewById(R.id.btnBack);
        btnLogout = findViewById(R.id.btnLogout);
        tvClickHere = findViewById(R.id.tvClickHere);
        itemProfile = findViewById(R.id.itemProfile);
        itemAddress = findViewById(R.id.itemAddress);
        itemwishlist = findViewById(R.id.itemWishlist);
        itemOrder = findViewById(R.id.itemOrder);
        tvUsername = findViewById(R.id.tvUsername);
        avatarText = findViewById(R.id.avatarText);
        apiManager api = new apiManager();
        api.getUserById(userId, new retrofit2.Callback<com.example.app_jewelry.Service.DTO.reponse.UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse user = response.body();
                    tvUsername.setText(user.getFullName());

                    if (user.getFullName() != null && !user.getFullName().isEmpty()) {
                        avatarText.setText(user.getFullName().substring(0, 1).toUpperCase());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
        // Quay lại
        btnBack.setOnClickListener(v -> finish());

        // Chuyển đến Wishlist
        itemwishlist.setOnClickListener(v ->
                startActivity(new Intent(UserAccountActivity.this, WishlistActivity.class)));

        // Chuyển đến Orders
        itemOrder.setOnClickListener(v ->
                startActivity(new Intent(UserAccountActivity.this, MyOrderActivity.class)));

        // Đăng xuất
        btnLogout.setOnClickListener(v -> {
            spManager.clearLoginSession();
            Intent intent = new Intent(UserAccountActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Profile
        itemProfile.setOnClickListener(v -> {
            Intent intent = new Intent(UserAccountActivity.this, ProfileActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        // Địa chỉ
        itemAddress.setOnClickListener(v -> {
            Intent intent = new Intent(UserAccountActivity.this, AddressActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
    }
}
