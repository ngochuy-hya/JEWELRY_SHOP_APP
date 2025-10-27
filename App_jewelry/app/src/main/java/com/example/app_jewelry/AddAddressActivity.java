package com.example.app_jewelry;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_jewelry.Service.API.ApiClient;
import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.API.apiService;
import com.example.app_jewelry.entity.*;
import com.example.app_jewelry.utils.SharedPreferencesManager;
import com.example.app_jewelry.utils.InputValidator;


import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {

    private TextView tvState, tvCity, tvArea;
    private apiService apiService;

    private Province selectedProvince;
    private District selectedDistrict;
    private Ward selectedWard;
    private ImageView btnBack;
    private EditText edtContactName, edtContactNumber, edtEmail, edtAddressLine, edtLandmark;
    private CheckBox chkIsDefault;
    private Button btnSaveAddress;
    private apiManager api = new apiManager();

    private Address editAddress = null;
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        // Init Views
        tvState = findViewById(R.id.tvState);
        tvCity = findViewById(R.id.tvCity);
        tvArea = findViewById(R.id.tvArea);
        btnBack = findViewById(R.id.btnBack);
        edtContactName = findViewById(R.id.edtContactName);
        edtContactNumber = findViewById(R.id.edtContactNumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtAddressLine = findViewById(R.id.edtAddressLine);
        edtLandmark = findViewById(R.id.edtLandmark);
        chkIsDefault = findViewById(R.id.chkIsDefault);
        btnSaveAddress = findViewById(R.id.btnSaveAddress);

        apiService = ApiClient.getApiService();

        userId = new SharedPreferencesManager(this).getUserId();
        editAddress = (Address) getIntent().getSerializableExtra("address");

        if (editAddress != null) {
            edtContactName.setText(editAddress.getReceiverName());
            edtContactNumber.setText(editAddress.getPhone());
            edtEmail.setText(editAddress.getEmail());
            edtAddressLine.setText(editAddress.getAddressLine());
            edtLandmark.setText(editAddress.getLandmark());
            tvArea.setText(editAddress.getArea());
            tvCity.setText(editAddress.getCity());
            tvState.setText(editAddress.getState());
            chkIsDefault.setChecked(editAddress.isDefault());
            btnSaveAddress.setText("Update Address");
        }

        btnBack.setOnClickListener(v -> finish());

        tvState.setOnClickListener(v -> loadProvinces());
        tvCity.setOnClickListener(v -> {
            if (selectedProvince != null) loadDistricts(selectedProvince.getCode());
            else Toast.makeText(this, "Vui lòng chọn tỉnh trước", Toast.LENGTH_SHORT).show();
        });
        tvArea.setOnClickListener(v -> {
            if (selectedDistrict != null) loadWards(selectedDistrict.getCode());
            else Toast.makeText(this, "Vui lòng chọn quận trước", Toast.LENGTH_SHORT).show();
        });

        btnSaveAddress.setOnClickListener(v -> {
            String name = edtContactName.getText().toString().trim();
            String phone = edtContactNumber.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String addressLine = edtAddressLine.getText().toString().trim();
            String landmark = edtLandmark.getText().toString().trim();
            String area = tvArea.getText().toString();
            String city = tvCity.getText().toString();
            String state = tvState.getText().toString();
            boolean isDefault = chkIsDefault.isChecked();

            if (name.isEmpty() || phone.isEmpty() || addressLine.isEmpty() || city.isEmpty() || state.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin bắt buộc", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!InputValidator.isValidName(name)) {
                edtContactName.setError("Tên không hợp lệ (ít nhất 2 ký tự, không chứa số)");
                return;
            }

            if (!InputValidator.isValidPhone(phone)) {
                edtContactNumber.setError("Số điện thoại không hợp lệ (10 số, bắt đầu bằng 0)");
                return;
            }

            if (!email.isEmpty() && !InputValidator.isValidEmail(email)) {
                edtEmail.setError("Email không đúng định dạng");
                return;
            }

            if (addressLine.isEmpty()) {
                edtAddressLine.setError("Địa chỉ chi tiết không được để trống");
                return;
            }

            if (state.isEmpty() || city.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn đầy đủ tỉnh/thành và quận/huyện", Toast.LENGTH_SHORT).show();
                return;
            }

            Address address = new Address();
            address.setReceiverName(name);
            address.setPhone(phone);
            address.setEmail(email);
            address.setAddressLine(addressLine);
            address.setLandmark(landmark);
            address.setArea(area);
            address.setCity(city);
            address.setState(state);
            address.setDefault(isDefault);

            // Gọi API thêm hoặc cập nhật
            if (editAddress != null) {
                api.updateAddress(editAddress.getAddressId(), address, new Callback<Address>() {
                    @Override
                    public void onResponse(Call<Address> call, Response<Address> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AddAddressActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddAddressActivity.this, "Lỗi khi cập nhật", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Address> call, Throwable t) {
                        Toast.makeText(AddAddressActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                api.addAddress(userId, address, new Callback<Address>() {
                    @Override
                    public void onResponse(Call<Address> call, Response<Address> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AddAddressActivity.this, "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(AddAddressActivity.this, "Lỗi khi thêm địa chỉ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Address> call, Throwable t) {
                        Toast.makeText(AddAddressActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Load tỉnh
    private void loadProvinces() {
        apiService.getAllProvinces().enqueue(new Callback<List<Province>>() {
            @Override public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                if (response.isSuccessful()) showProvinceDialog(response.body());
            }
            @Override public void onFailure(Call<List<Province>> call, Throwable t) {
                Toast.makeText(AddAddressActivity.this, "Không thể tải tỉnh/thành phố", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProvinceDialog(List<Province> provinces) {
        List<String> names = new ArrayList<>();
        for (Province p : provinces) names.add(p.getName());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_dialog_small, names);
        new AlertDialog.Builder(this)
                .setTitle("Chọn Tỉnh/Thành phố")
                .setAdapter(adapter, (dialog, which) -> {
                    selectedProvince = provinces.get(which);
                    tvState.setText(selectedProvince.getName());
                    tvCity.setText(""); tvArea.setText("");
                    selectedDistrict = null; selectedWard = null;
                }).show();
    }

    // Load quận/huyện
    private void loadDistricts(int provinceCode) {
        apiService.getProvinceWithDistricts(provinceCode, 2).enqueue(new Callback<Province>() {
            @Override public void onResponse(Call<Province> call, Response<Province> response) {
                if (response.isSuccessful()) showDistrictDialog(response.body().getDistricts());
            }
            @Override public void onFailure(Call<Province> call, Throwable t) {
                Toast.makeText(AddAddressActivity.this, "Không thể tải quận/huyện", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDistrictDialog(List<District> districts) {
        List<String> names = new ArrayList<>();
        for (District d : districts) names.add(d.getName());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_dialog_small, names);
        new AlertDialog.Builder(this)
                .setTitle("Chọn Quận/Huyện")
                .setAdapter(adapter, (dialog, which) -> {
                    selectedDistrict = districts.get(which);
                    tvCity.setText(selectedDistrict.getName());
                    tvArea.setText(""); selectedWard = null;
                }).show();
    }

    // Load phường/xã
    private void loadWards(int districtCode) {
        apiService.getDistrictWithWards(districtCode, 2).enqueue(new Callback<District>() {
            @Override public void onResponse(Call<District> call, Response<District> response) {
                if (response.isSuccessful()) showWardDialog(response.body().getWards());
            }
            @Override public void onFailure(Call<District> call, Throwable t) {
                Toast.makeText(AddAddressActivity.this, "Không thể tải phường/xã", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showWardDialog(List<Ward> wards) {
        List<String> names = new ArrayList<>();
        for (Ward w : wards) names.add(w.getName());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_dialog_small, names);
        new AlertDialog.Builder(this)
                .setTitle("Chọn Phường/Xã")
                .setAdapter(adapter, (dialog, which) -> {
                    selectedWard = wards.get(which);
                    tvArea.setText(selectedWard.getName());
                }).show();
    }
}
