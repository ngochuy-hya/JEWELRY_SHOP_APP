package com.example.app_jewelry.Service.DTO.reponse;

import java.util.List;

public class FilterOptionsResponse {
    private List<String> brands;
    private List<String> categories;
    private List<String> sizes;

    // Getter + Setter
    public List<String> getBrands() { return brands; }
    public void setBrands(List<String> brands) { this.brands = brands; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }


    public List<String> getSizes() { return sizes; }
    public void setSizes(List<String> sizes) { this.sizes = sizes; }
}

