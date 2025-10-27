package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.fragment.HomeFragment;
import com.example.app_jewelry.utils.SharedPreferencesManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar topAppBar;
    private int userId = -1;
    private apiManager api = new apiManager();
    private SharedPreferencesManager spManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Khởi tạo SharedPreferences
        spManager = new SharedPreferencesManager(this);
        userId = spManager.getUserId();

        // Khởi tạo Toolbar
        topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);

        // Áp dụng padding top nếu dùng EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(topAppBar, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        // Nút avatar
        topAppBar.setNavigationOnClickListener(v -> {
            if (userId == -1 || userId == 0) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            } else {
                Intent intent = new Intent(MainActivity.this, UserAccountActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        // Bottom Navigation xử lý sự kiện
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        BadgeDrawable badge = bottomNavigation.getOrCreateBadge(R.id.fragment_cart);
        bottomNavigation.setOnItemSelectedListener(this::onBottomNavItemSelected);
    }

    private boolean onBottomNavItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.fragment_homepage) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        else if (id == R.id.fragment_aboutus) {
            startActivity(new Intent(this, AboutUsActivity.class));
            return true;
        }

        // Với các mục cần đăng nhập
        if (userId == -1 || userId == 0) {
            startActivity(new Intent(this, LoginActivity.class));
            return false;
        }

        if (id == R.id.fragment_favorite) {
            startActivity(new Intent(this, WishlistActivity.class));
            return true;
        } else if (id == R.id.fragment_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        } else if (id == R.id.fragment_orders) {
            startActivity(new Intent(this, MyOrderActivity.class));
            return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Cập nhật userId mới nhất khi quay lại từ LoginActivity
        userId = spManager.getUserId();

        updateCartBadge();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_chat) {
            startActivity(new Intent(this, ChatActivity.class));
            return true;
        } else if (id == R.id.action_search) {
            startActivity(new Intent(this, SearchActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateCartBadge() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        BadgeDrawable badge = bottomNavigation.getOrCreateBadge(R.id.fragment_cart);

        if (userId == -1 || userId == 0) {
            badge.setVisible(false);
            return;
        }

        api.getCartItemCount(userId, new retrofit2.Callback<Integer>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Integer> call, @NonNull retrofit2.Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int count = response.body();
                    if (count > 0) {
                        badge.setVisible(true);
                        badge.setNumber(count);
                    } else {
                        badge.setVisible(false);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<Integer> call, @NonNull Throwable t) {
                Log.e("CartBadge", "Failed to load cart item count: " + t.getMessage());
            }
        });
    }
}
