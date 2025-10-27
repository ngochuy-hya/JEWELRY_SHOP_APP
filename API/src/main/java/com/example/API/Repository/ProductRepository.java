package com.example.API.Repository;

import com.example.API.DTO.Reponse.BestsellerProductResponse;
import com.example.API.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // ✅ thêm dòng này
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    List<Product> findTop10ByIsActiveTrueOrderByProductIdDesc();

    @Query("""
        SELECT new com.example.API.DTO.Reponse.BestsellerProductResponse(
            p.productId,
            p.name,
            p.mainImage,
            SUM(oi.quantity),
            CAST(COALESCE(AVG(r.rating), 0.0) AS double),
            COUNT(r.reviewId)
        )
        FROM OrderItem oi
        JOIN oi.variant pv
        JOIN pv.product p
        LEFT JOIN Review r ON r.product = p
        GROUP BY p.productId, p.name, p.mainImage
        ORDER BY SUM(oi.quantity) DESC
    """)
    List<BestsellerProductResponse> findBestsellerProducts();

    @Query("SELECT p FROM Product p WHERE p.brand.brandId = :brandId AND p.isActive = true")
    List<Product> findByBrandId(@Param("brandId") int brandId);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId AND p.isActive = true")
    List<Product> findByCategoryId(@Param("categoryId") int categoryId);

    @Query("SELECT p.mainImage FROM Product p WHERE p.productId = :productId")
    String findMainImageByProductId(@Param("productId") int productId);

    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.brand " +
            "JOIN FETCH p.category " +
            "WHERE p.productId = :productId")
    Optional<Product> findProductWithBrandAndCategory(@Param("productId") Integer productId);

    List<Product> findByNameContainingIgnoreCase(String name);
}
