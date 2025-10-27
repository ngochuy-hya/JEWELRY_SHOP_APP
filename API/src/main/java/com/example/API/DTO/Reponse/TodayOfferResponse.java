    package com.example.API.DTO.Reponse;



    public interface TodayOfferResponse {
        int getProductId();
        String getName();
        String getMainImage();
        double getOriginalPrice();
        double getCurrentPrice();
        Double getRating();
        Long getReviewCount();
    }
