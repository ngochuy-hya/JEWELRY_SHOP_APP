package com.example.API.Repository;

import com.example.API.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.product.productId = :productId")
    float getAverageRatingByProductId(Integer productId);

    int countByProductProductId(Integer productId);

    @Query("SELECT r FROM Review r " +
            "LEFT JOIN FETCH r.imageList " +
            "JOIN FETCH r.user " +
            "WHERE r.product.productId = :productId " +
            "ORDER BY r.createdAt DESC")
    List<Review> findReviewsWithImagesByProductId(@Param("productId") Integer productId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.productId = :productId")
    Double getAverageRating(@Param("productId") Integer productId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.productId = :productId")
    Long getReviewCount(@Param("productId") Integer productId);

    @Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.product.productId = :productId GROUP BY r.rating")
    List<Object[]> countStarsByRating(@Param("productId") Integer productId);

    boolean existsByUser_UserIdAndProduct_ProductId(Integer userId, Integer productId);
    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.user.userId = :userId AND r.product.productId = :productId")
    boolean hasUserReviewedProduct(@Param("userId") int userId, @Param("productId") int productId);

}
