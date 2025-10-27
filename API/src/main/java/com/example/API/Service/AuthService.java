package com.example.API.Service;

import com.example.API.DTO.Reponse.LoginResponse;
import com.example.API.DTO.Request.LoginRequest;
import com.example.API.DTO.Request.RegisterRequest;
import com.example.API.DTO.Reponse.RegisterResponse;
import com.example.API.Entity.Conversation;
import com.example.API.Entity.Role;
import com.example.API.Entity.User;
import com.example.API.Repository.ConversationRepository;
import com.example.API.Repository.UserRepository;
import com.example.API.Utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final ConversationRepository conversationRepository;

    // Map tạm lưu đăng ký đang chờ OTP xác nhận: key = email
    private final Map<String, RegistrationInfo> pendingRegistrations = new ConcurrentHashMap<>();

    // Map tạm lưu OTP quên mật khẩu: key = email
    private final Map<String, OtpInfo> passwordResetOtps = new ConcurrentHashMap<>();

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       EmailService emailService,
                       ConversationRepository conversationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
        this.conversationRepository = conversationRepository;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!request.getPassword().equals(user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }
//        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
//            throw new RuntimeException("Invalid email or password");
//        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse(user.getUserId(), token);
    }

    // Hàm đăng ký tạo OTP và gửi mail
    public RegisterResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Password and confirm password do not match");
        }

        if (userRepository.existsByEmail(request.getEmail()) || pendingRegistrations.containsKey(request.getEmail())) {
            throw new RuntimeException("Email already exists or pending verification");
        }

        String otp = generateOtp();

        emailService.sendOtpEmail(request.getEmail(), otp);

        RegistrationInfo info = new RegistrationInfo(
                request.getFullName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                otp,
                LocalDateTime.now().plusMinutes(10)
        );
        pendingRegistrations.put(request.getEmail(), info);

        return new RegisterResponse("OTP sent to your email");
    }

    // Hàm xác nhận OTP và tạo user thật
    public RegisterResponse verifyOtp(String email, String otp) {
        RegistrationInfo info = pendingRegistrations.get(email);
        if (info == null) {
            throw new RuntimeException("No pending registration found");
        }
        if (info.getOtpExpiry().isBefore(LocalDateTime.now())) {
            pendingRegistrations.remove(email);
            throw new RuntimeException("OTP expired");
        }
        if (!info.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        User user = new User();
        Role role = new Role();
        role.setRoleId(1);
        user.setRole(role);
        user.setFullName(info.getFullName());
        user.setEmail(info.getEmail());
        user.setPasswordHash(info.getPasswordHash());
        userRepository.save(user);

        // Tạo conversation mặc định
        Conversation conversation = new Conversation();
        conversation.setUser(user);
        conversation.setIsAiEnabled(false); // hoặc true nếu mặc định bật AI
        conversation.setStatus("active");
        conversation.setCreatedAt(LocalDateTime.now());
        conversationRepository.save(conversation);

        pendingRegistrations.remove(email);
        return new RegisterResponse("Registration successful");
    }


    // Gửi OTP quên mật khẩu
    public RegisterResponse sendResetPasswordOtp(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Email not found");
        }

        String otp = generateOtp();
        passwordResetOtps.put(email, new OtpInfo(otp, LocalDateTime.now().plusMinutes(10)));

        emailService.sendOtpEmail(email, otp);

        return new RegisterResponse("OTP sent to your email");
    }

    // Xác nhận OTP quên mật khẩu và đổi mật khẩu mới
    public RegisterResponse resetPassword(String email, String otp, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Password and confirm password do not match");
        }

        OtpInfo otpInfo = passwordResetOtps.get(email);
        if (otpInfo == null) {
            throw new RuntimeException("No OTP found for email");
        }
        if (otpInfo.getExpiry().isBefore(LocalDateTime.now())) {
            passwordResetOtps.remove(email);
            throw new RuntimeException("OTP expired");
        }
        if (!otpInfo.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        User user = userRepository.findByEmail(email);
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetOtps.remove(email);

        return new RegisterResponse("Password reset successful");
    }

    private String generateOtp() {
        int otp = 100000 + new Random().nextInt(900000);
        return String.valueOf(otp);
    }

    private static class RegistrationInfo {
        private final String fullName;
        private final String email;
        private final String passwordHash;
        private final String otp;
        private final LocalDateTime otpExpiry;

        public RegistrationInfo(String fullName, String email, String passwordHash, String otp, LocalDateTime otpExpiry) {
            this.fullName = fullName;
            this.email = email;
            this.passwordHash = passwordHash;
            this.otp = otp;
            this.otpExpiry = otpExpiry;
        }

        public String getFullName() { return fullName; }
        public String getEmail() { return email; }
        public String getPasswordHash() { return passwordHash; }
        public String getOtp() { return otp; }
        public LocalDateTime getOtpExpiry() { return otpExpiry; }
    }

    private static class OtpInfo {
        private final String otp;
        private final LocalDateTime expiry;

        public OtpInfo(String otp, LocalDateTime expiry) {
            this.otp = otp;
            this.expiry = expiry;
        }

        public String getOtp() { return otp; }
        public LocalDateTime getExpiry() { return expiry; }
    }
}
