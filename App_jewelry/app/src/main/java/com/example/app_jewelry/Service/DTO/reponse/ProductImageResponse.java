package com.example.app_jewelry.Service.DTO.reponse;

import java.util.List;

public class ProductImageResponse {
    private String mainImage;
    private List<String> additionalImages;

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public List<String> getAdditionalImages() {
        return additionalImages;
    }

    public void setAdditionalImages(List<String> additionalImages) {
        this.additionalImages = additionalImages;
    }
}
