package com.example.API.Controller;

import com.example.API.DTO.Reponse.LoginResponse;
import com.example.API.DTO.Reponse.RegisterResponse;
import com.example.API.DTO.Request.LoginRequest;
import com.example.API.DTO.Request.RegisterRequest;
import com.example.API.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new RegisterResponse(ex.getMessage()));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<RegisterResponse> verifyOtp(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String otp = body.get("otp");
            RegisterResponse response = authService.verifyOtp(email, otp);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new RegisterResponse(ex.getMessage()));
        }
    }

    // Gửi OTP reset mật khẩu
    @PostMapping("/forgot-password")
    public ResponseEntity<RegisterResponse> forgotPassword(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            RegisterResponse response = authService.sendResetPasswordOtp(email);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new RegisterResponse(ex.getMessage()));
        }
    }

    // Xác nhận OTP và reset mật khẩu
    @PostMapping("/reset-password")
    public ResponseEntity<RegisterResponse> resetPassword(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String otp = body.get("otp");
            String newPassword = body.get("newPassword");
            String confirmPassword = body.get("confirmPassword");
            RegisterResponse response = authService.resetPassword(email, otp, newPassword, confirmPassword);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new RegisterResponse(ex.getMessage()));
        }
    }
}
