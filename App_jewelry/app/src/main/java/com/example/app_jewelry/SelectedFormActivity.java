package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.adapter.AddressSelectedAdapter;
import com.example.app_jewelry.entity.Address;
import com.example.app_jewelry.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedFormActivity extends AppCompatActivity {

    private RecyclerView recyclerAddress;
    private AddressSelectedAdapter adapter;
    private Button btnAddAddress;
    private ImageView btnBack;
    private ActivityResultLauncher<Intent> addAddressLauncher;

    private List<Address> addressList = new ArrayList<>();
    private final apiManager api = new apiManager();
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selected_form);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userId = new SharedPreferencesManager(this).getUserId();
        btnBack = findViewById(R.id.btnBack);
        btnAddAddress = findViewById(R.id.btnAddAddress);
        recyclerAddress = findViewById(R.id.recyclerAddress);
        recyclerAddress.setLayoutManager(new LinearLayoutManager(this));
        addAddressLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        fetchAddresses();
                    }
                }
        );
        btnBack.setOnClickListener(v -> finish());
        btnAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(SelectedFormActivity.this, AddAddressActivity.class);
            addAddressLauncher.launch(intent);
        });

        fetchAddresses();
    }

    private void fetchAddresses() {
        int selectedAddressId = getIntent().getIntExtra("selectedAddressId", -1);

        api.getUserAddresses(userId, new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addressList.clear();
                    addressList.addAll(response.body());

                    int selectedIndex = -1;

                    if (selectedAddressId != -1) {
                        for (int i = 0; i < addressList.size(); i++) {
                            if (addressList.get(i).getAddressId() == selectedAddressId) {
                                selectedIndex = i;
                                break;
                            }
                        }
                    }

                    if (selectedIndex == -1) {
                        for (int i = 0; i < addressList.size(); i++) {
                            if (addressList.get(i).isDefault()) {
                                selectedIndex = i;
                                break;
                            }
                        }
                    }

                    adapter = new AddressSelectedAdapter(
                            SelectedFormActivity.this,
                            addressList,
                            selectedAddress -> {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("selectedAddress", selectedAddress);
                                setResult(RESULT_OK, resultIntent);
                                finish();
                            }
                    );

                    if (selectedIndex != -1) {
                        adapter.setSelectedPosition(selectedIndex);
                    }

                    recyclerAddress.setAdapter(adapter);
                } else {
                    Toast.makeText(SelectedFormActivity.this, "Không có địa chỉ nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                Toast.makeText(SelectedFormActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
