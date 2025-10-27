package com.example.app_jewelry.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "jewelry_app_prefs";
    private static final String KEY_TOKEN = "key_token";
    private static final String KEY_USER_ID = "key_user_id";

    private final SharedPreferences prefs;

    public SharedPreferencesManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Lưu token và userId
    public void saveLoginSession(int userId, String token) {
        prefs.edit()
                .putInt(KEY_USER_ID, userId)
                .putString(KEY_TOKEN, token)
                .apply();
    }

    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    // Lấy userId
    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    // Xóa token và userId (đăng xuất)
    public void clearLoginSession() {
        prefs.edit()
                .remove(KEY_TOKEN)
                .remove(KEY_USER_ID)
                .apply();
    }
}
