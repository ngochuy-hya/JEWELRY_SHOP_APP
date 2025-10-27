package com.example.API.DTO.Reponse;

import java.util.List;

public class ProductImageResponse {
    private String mainImage;
    private List<String> additionalImages;

    public ProductImageResponse() {}

    public ProductImageResponse(String mainImage, List<String> additionalImages) {
        this.mainImage = mainImage;
        this.additionalImages = additionalImages;
    }

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
