package com.example.API.Repository;

import com.example.API.Entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    @Query("SELECT pi.imageUrl FROM ProductImage pi WHERE pi.product.productId = :productId")
    List<String> findImageUrlsByProductId(@Param("productId") int productId);
    List<ProductImage> findByProduct_ProductId(Integer productId);
}
