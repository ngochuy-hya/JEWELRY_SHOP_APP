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
import com.example.app_jewelry.Service.DTO.request.RegisterRequest;
import com.example.app_jewelry.utils.InputValidator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    private apiManager apiManager;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });

        apiManager = new apiManager();

        signUpButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (fullName.isEmpty()) {
                fullNameEditText.setError("Please enter full name");
                return;
            }
            if (email.isEmpty()) {
                emailEditText.setError("Please enter email");
                return;
            }
            if (password.isEmpty()) {
                passwordEditText.setError("Please enter password");
                return;
            }
            if (!password.equals(confirmPassword)) {
                confirmPasswordEditText.setError("Passwords do not match");
                return;
            }
            if (!InputValidator.isValidName(fullName)) {
                fullNameEditText.setError("Invalid name. Must be at least 2 letters.");
                return;
            }
            if (!InputValidator.isValidEmail(email)) {
                emailEditText.setError("Invalid email format.");
                return;
            }
            if (!InputValidator.isStrongPassword(password)) {
                passwordEditText.setError("Password must be at least 8 characters, include uppercase and digit.");
                return;
            }
            if (!password.equals(confirmPassword)) {
                confirmPasswordEditText.setError("Passwords do not match.");
                return;
            }


            RegisterRequest request = new RegisterRequest(fullName, email, password, confirmPassword);

            apiManager.register(request, new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpActivity.this, EnterOTPActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Register failed: " + (response.errorBody() != null ? response.errorBody().toString() : ""), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
