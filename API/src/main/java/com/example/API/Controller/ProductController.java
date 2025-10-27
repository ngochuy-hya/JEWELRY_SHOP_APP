package com.example.API.Controller;

import com.example.API.DTO.Reponse.*;
import com.example.API.DTO.Request.BestsellerRequest;
import com.example.API.DTO.Request.ProductArrivalRequest;
import com.example.API.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/arrivals")
    public ResponseEntity<List<ProductArrivalResponse>> getProductArrivals(@RequestBody ProductArrivalRequest request) {
        List<ProductArrivalResponse> result = productService.getNewArrivalProducts(request.getUserId());
        return ResponseEntity.ok(result);
    }
    @PostMapping("/bestsellers")
    public ResponseEntity<List<BestsellerProductResponse>> getBestsellerProducts(@RequestBody BestsellerRequest request) {
        List<BestsellerProductResponse> result = productService.getBestsellerProducts(request.getUserId());
        return ResponseEntity.ok(result);
    }
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<ProductResponse>> getProductsByBrand(
            @PathVariable int brandId,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String sortOption) {

        int resolvedUserId = (userId != null) ? userId : -1;
        List<ProductResponse> products = productService.getProductsByBrandId(brandId, resolvedUserId, sortOption);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @PathVariable int categoryId,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String sortOption) {

        int resolvedUserId = (userId != null) ? userId : -1;
        List<ProductResponse> products = productService.getProductsByCategoryId(categoryId, resolvedUserId, sortOption);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getProductsByType(
            @RequestParam String type,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String sortOption) {

        int resolvedUserId = (userId != null) ? userId : -1;
        List<ProductResponse> products = productService.getProductsByType(type, resolvedUserId, sortOption);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{productId}/images")
    public ResponseEntity<ProductImageResponse> getProductImages(@PathVariable int productId) {
        ProductImageResponse images = productService.getProductImages(productId);
        return ResponseEntity.ok(images);
    }
    @GetMapping("/{productId}/detail")
    public ResponseEntity<?> getProductDetail(
            @PathVariable int productId,
            @RequestParam(required = false) Integer userId) {
        int resolvedUserId = (userId != null) ? userId : -1;
        try {
            ProductDetailResponse detail = productService.getProductDetail(productId, resolvedUserId);
            return ResponseEntity.ok(detail);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi xảy ra: " + e.getMessage());
        }
    }
    @GetMapping("/filters")
    public ResponseEntity<FilterOptionsResponse> getFilterOptions() {
        return ResponseEntity.ok(productService.getFilterOptions());
    }
    @GetMapping("/filter")
    public List<ProductResponse> filterProducts(
            @RequestParam(required = false) List<String> brands,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) String sort,
            @RequestParam Integer userId
    ) {
        return productService.getFilteredProducts(brands, categories, sizes, minPrice, maxPrice, userId, sort);
    }
    @GetMapping("/api/products/by-ids")
    public List<ProductResponse> getProductsByIds(
            @RequestParam List<Integer> ids,
            @RequestParam(required = false) Integer userId
    ) {
        return productService.getProductsByIds(ids, userId);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ResultSearchResponse>> searchProducts(@RequestParam String query) {
        List<ResultSearchResponse> results = productService.searchProductsByName(query);
        return ResponseEntity.ok(results);
    }
}

