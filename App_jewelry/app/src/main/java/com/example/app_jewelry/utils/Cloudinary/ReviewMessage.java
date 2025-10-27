package com.example.app_jewelry.utils.Cloudinary;

import android.net.Uri;

public class ReviewMessage {
    private Uri localUri;
    private String uploadedUrl;
    private boolean isUploading;

    public ReviewMessage(Uri localUri) {
        this.localUri = localUri;
        this.isUploading = true;
    }

    // Getters and setters
    public Uri getLocalUri() {
        return localUri;
    }

    public void setUploadedUrl(String uploadedUrl) {
        this.uploadedUrl = uploadedUrl;
    }

    public String getUploadedUrl() {
        return uploadedUrl;
    }

    public boolean isUploading() {
        return isUploading;
    }

    public void setUploading(boolean uploading) {
        isUploading = uploading;
    }
}
