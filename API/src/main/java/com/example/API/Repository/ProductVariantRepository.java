package com.example.API.Repository;

import com.example.API.Entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    Optional<ProductVariant> findTopByProduct_ProductIdOrderByPriceAsc(Integer productId);
    List<ProductVariant> findByProduct_ProductId(Integer productId);
    @Query("SELECT DISTINCT pv.color FROM ProductVariant pv WHERE pv.color IS NOT NULL")
    List<String> findDistinctColors();

    @Query("SELECT DISTINCT pv.size FROM ProductVariant pv WHERE pv.size IS NOT NULL")
    List<String> findDistinctSizes();
    Optional<ProductVariant> findTopByProduct_ProductIdAndStockGreaterThanOrderByPriceAsc(int productId, int stock);
    boolean existsByProduct_ProductIdAndStockGreaterThan(int productId, int stock);
    List<ProductVariant> findByProduct_ProductIdAndStockGreaterThan(int productId, int stock);

}

