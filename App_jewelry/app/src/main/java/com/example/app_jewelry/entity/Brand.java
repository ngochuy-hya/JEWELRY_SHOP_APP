package com.example.app_jewelry.entity;

import com.google.gson.annotations.SerializedName;

public class Brand {
    @SerializedName("brandId")
    private int brandID;

    private String name;

    @SerializedName("logoUrl")
    private String logoUrl;

    // Constructor
    public Brand(int brandID, String name, String logoUrl) {
        this.brandID = brandID;
        this.name = name;
        this.logoUrl = logoUrl;
    }

    // Getters and Setters
    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
