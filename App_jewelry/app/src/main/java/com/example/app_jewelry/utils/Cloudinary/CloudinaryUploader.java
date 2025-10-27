package com.example.app_jewelry.utils.Cloudinary;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Map;

public class CloudinaryUploader {

    public interface UploadCallback {
        void onSuccess(String imageUrl);
        void onError(String errorMessage);
    }

    public static void uploadImage(Context context, Uri imageUri, UploadCallback callback) {
        new Thread(() -> {
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
                byte[] imageBytes = IOUtils.toByteArray(inputStream);

                Map result = CloudinaryConfig.getInstance().uploader().upload(
                        imageBytes,
                        Map.of("upload_preset", CloudinaryConfig.getUploadPreset())
                );

                String imageUrl = (String) result.get("secure_url");

                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(imageUrl));
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }
}
