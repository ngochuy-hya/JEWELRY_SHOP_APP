package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.adapter.AddressAdapter;
import com.example.app_jewelry.entity.Address;
import com.example.app_jewelry.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {

    private ImageView btnBack;
    private Button btnAddAddress;
    private RecyclerView recyclerAddresses;
    private LinearLayout layoutEmpty;
    private AddressAdapter addressAdapter;
    private List<Address> addressList = new ArrayList<>();
    private int userId;
    private final apiManager api = new apiManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_address);

        userId = new SharedPreferencesManager(this).getUserId();

        btnBack = findViewById(R.id.btnBack);
        btnAddAddress = findViewById(R.id.btnAddAddress);
        recyclerAddresses = findViewById(R.id.recyclerAddresses);
        layoutEmpty = findViewById(R.id.layoutEmpty);

        recyclerAddresses.setLayoutManager(new LinearLayoutManager(this));

        btnBack.setOnClickListener(v -> finish());

        btnAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadAddresses();
    }

    private void loadAddresses() {
        api.getUserAddresses(userId, new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addressList = response.body();
                    Collections.sort(addressList, (a1, a2) -> Boolean.compare(!a1.isDefault(), !a2.isDefault())); // default lên đầu

                    if (addressList.isEmpty()) {
                        layoutEmpty.setVisibility(View.VISIBLE);
                        recyclerAddresses.setVisibility(View.GONE);
                    } else {
                        layoutEmpty.setVisibility(View.GONE);
                        recyclerAddresses.setVisibility(View.VISIBLE);

                        addressAdapter = new AddressAdapter(addressList, new AddressAdapter.OnAddressActionListener() {
                            @Override
                            public void onEdit(Address address) {
                                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                                intent.putExtra("userId", userId);
                                intent.putExtra("isEdit", true);
                                intent.putExtra("address", address);
                                startActivity(intent);
                            }

                            @Override
                            public void onRemove(Address address) {
                                api.deleteAddress(address.getAddressId(), new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(AddressActivity.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                            loadAddresses();
                                        } else {
                                            Toast.makeText(AddressActivity.this, "Không thể xoá địa chỉ", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(AddressActivity.this, "Lỗi kết nối khi xoá", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                        recyclerAddresses.setAdapter(addressAdapter);
                    }
                } else {
                    Toast.makeText(AddressActivity.this, "Không thể lấy danh sách địa chỉ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                Toast.makeText(AddressActivity.this, "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAddresses();
    }
}
