package com.example.app_jewelry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.CartItemResponse;
import com.example.app_jewelry.Service.DTO.reponse.VoucherResponse;
import com.example.app_jewelry.entity.Address;
import com.example.app_jewelry.utils.SharedPreferencesManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormAddressActivity extends AppCompatActivity {

    private TextView txtSelectAddress;
    private ImageView btnBack;
    private Button btnNext;

    private EditText edtContactName, edtContactNumber, edtEmail,
            edtState, edtCity, edtArea, edtAddressLine, edtLandmark;

    private Address selectedAddress;
    private List<CartItemResponse> selectedItems;
    private VoucherResponse appliedVoucher;

    private final apiManager api = new apiManager();
    private int userId;

    private ActivityResultLauncher<Intent> addressResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_address);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userId = new SharedPreferencesManager(this).getUserId();
        // Ánh xạ view
        btnBack = findViewById(R.id.btnBack);
        txtSelectAddress = findViewById(R.id.tvSelectAddress);
        btnNext = findViewById(R.id.btnNext);
        edtContactName = findViewById(R.id.edtContactName);
        edtContactNumber = findViewById(R.id.edtContactNumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtState = findViewById(R.id.edtState);
        edtCity = findViewById(R.id.edtCity);
        edtArea = findViewById(R.id.edtArea);
        edtAddressLine = findViewById(R.id.edtAddressLine);
        edtLandmark = findViewById(R.id.edtLandmark);

        btnBack.setOnClickListener(v -> finish());

        // Nhận dữ liệu từ CartActivity
        selectedItems = (List<CartItemResponse>) getIntent().getSerializableExtra("selectedItems");
        appliedVoucher = (VoucherResponse) getIntent().getSerializableExtra("appliedVoucher");

        addressResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedAddress = (Address) result.getData().getSerializableExtra("selectedAddress");
                        if (selectedAddress != null) {
                            fillAddressToForm(selectedAddress);
                        }
                    }
                }
        );

        txtSelectAddress.setOnClickListener(v -> {
            Intent intent = new Intent(FormAddressActivity.this, SelectedFormActivity.class);
            if (selectedAddress != null) {
                intent.putExtra("selectedAddressId", selectedAddress.getAddressId());
            }
            addressResultLauncher.launch(intent);
        });

        btnNext.setOnClickListener(v -> {
            if (selectedAddress == null) {
                Toast.makeText(this, "Vui lòng chọn địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(FormAddressActivity.this, CheckoutPaymentActivity.class);
            intent.putExtra("selectedItems", (Serializable) selectedItems);
            intent.putExtra("selectedAddress", selectedAddress);
            intent.putExtra("appliedVoucher", appliedVoucher);
            startActivity(intent);
        });

        loadDefaultAddress();
    }

    private void fillAddressToForm(Address address) {
        edtContactName.setText(address.getReceiverName());
        edtContactNumber.setText(address.getPhone());
        edtEmail.setText(address.getEmail());
        edtState.setText(address.getState());
        edtCity.setText(address.getCity());
        edtArea.setText(address.getArea());
        edtAddressLine.setText(address.getAddressLine());
        edtLandmark.setText(address.getLandmark());
    }

    private void loadDefaultAddress() {
        api.getUserAddresses(userId, new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Address addr : response.body()) {
                        if (addr.isDefault()) {
                            selectedAddress = addr;
                            fillAddressToForm(addr);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
            }
        });
    }
}
