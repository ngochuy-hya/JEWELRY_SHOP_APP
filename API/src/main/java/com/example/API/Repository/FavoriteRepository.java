package com.example.API.Repository;

import com.example.API.DTO.Reponse.ProductResponse;
import com.example.API.Entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    boolean existsByUser_UserIdAndProduct_ProductId(Integer userId, Integer productId);
    void deleteByUser_UserIdAndProduct_ProductId(Integer userId, Integer productId);
        @Query("""
    SELECT new com.example.API.DTO.Reponse.ProductResponse(
        p.productId,
        p.name,
        p.mainImage,
        COALESCE(MIN(pv.price), 0),
        COALESCE(
            MIN(
                CASE
                    WHEN fd.isActive = true AND CURRENT_TIMESTAMP BETWEEN fd.startTime AND fd.endTime
                    THEN fd.discountPrice
                    ELSE pv.price
                END
            ), 0
        ),
        COALESCE(AVG(r.rating), 0),
        COALESCE(COUNT(r.reviewId), 0),
        true
    )
    FROM Favorite f
    JOIN Product p ON f.product.productId = p.productId
    LEFT JOIN ProductVariant pv ON pv.product.productId = p.productId
    LEFT JOIN FlashDeal fd ON fd.product.productId = p.productId
    LEFT JOIN Review r ON r.product.productId = p.productId
    WHERE f.user.userId = :userId
    GROUP BY p.productId, p.name, p.mainImage
    """)
        List<ProductResponse> findFavoriteProductsByUserId(Integer userId);


}