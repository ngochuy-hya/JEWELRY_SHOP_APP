package com.example.app_jewelry.utils;

import android.util.Patterns;

public class InputValidator {

    public static boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("0\\d{9}");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isStrongPassword(String password) {
        return password != null && password.matches("^(?=.*[A-Z])(?=.*\\d).{8,}$");
    }

    public static boolean isValidName(String name) {
        return name != null && name.matches("^[a-zA-ZÀ-ỹ\\s]{2,}$");
    }
}
