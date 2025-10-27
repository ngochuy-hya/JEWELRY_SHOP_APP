package com.example.app_jewelry.utils.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryConfig {

    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", "dn4l1otfz");
            config.put("api_key", "473799745296454");
            config.put("api_secret", "PlNyuFd5Dk0qn53vCcFdW7nqyo0");
            cloudinary = new Cloudinary(config);
        }
        return cloudinary;
    }

    public static String getUploadPreset() {
        return "my_preset";
    }
}