package com.example.API.Repository;

import com.example.API.DTO.Reponse.TodayOfferResponse;
import com.example.API.Entity.FlashDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlashDealRepository extends JpaRepository<FlashDeal, Integer> {

    // Truy vấn lấy danh sách Today's Deals đang hoạt động
    @Query("""
        SELECT 
            p.productId AS productId,
            p.name AS name,
            p.mainImage AS mainImage,
            v.price AS originalPrice,
            f.discountPrice AS currentPrice,
            COALESCE(AVG(r.rating), 0.0) AS rating,
            COUNT(r.reviewId) AS reviewCount
        FROM FlashDeal f
        JOIN f.product p
        JOIN ProductVariant v ON v.product = p
        LEFT JOIN Review r ON r.product = p
        WHERE f.isActive = true
          AND CURRENT_TIMESTAMP BETWEEN f.startTime AND f.endTime
        GROUP BY p.productId, p.name, p.mainImage, v.price, f.discountPrice
    """)
    List<TodayOfferResponse> getTodayOffers();

    // Truy vấn lấy FlashDeal mới nhất của một sản phẩm
    Optional<FlashDeal> findTopByProduct_ProductIdAndIsActiveTrueOrderByStartTimeDesc(Integer productId);

    // (Có thể giữ lại hoặc gộp với getTodayOffers)
    @Query("""
        SELECT 
            p.productId AS productId,
            p.name AS name,
            p.mainImage AS mainImage,
            v.price AS originalPrice,
            f.discountPrice AS currentPrice,
            COALESCE(AVG(r.rating), 0.0) AS rating,
            COUNT(r.reviewId) AS reviewCount
        FROM FlashDeal f
        JOIN f.product p
        JOIN ProductVariant v ON v.product = p
        LEFT JOIN Review r ON r.product = p
        WHERE f.isActive = true
          AND CURRENT_TIMESTAMP BETWEEN f.startTime AND f.endTime
        GROUP BY p.productId, p.name, p.mainImage, v.price, f.discountPrice
    """)
    List<TodayOfferResponse> getAllTodayOffers();

    @Query("SELECT f FROM FlashDeal f WHERE f.product.productId = :productId AND f.isActive = true AND :now BETWEEN f.startTime AND f.endTime")
    Optional<FlashDeal> findActiveDealForProduct(Integer productId, LocalDateTime now);
}