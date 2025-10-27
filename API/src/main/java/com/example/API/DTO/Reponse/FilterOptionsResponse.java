package com.example.API.DTO.Reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterOptionsResponse {
    private List<String> brands;
    private List<String> categories;
    private List<String> colors;
    private List<String> sizes;
}