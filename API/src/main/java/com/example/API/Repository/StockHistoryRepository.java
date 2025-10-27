package com.example.API.Repository;

import com.example.API.Entity.StockHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockHistoryRepository extends CrudRepository<StockHistory, Integer> {

    @Query(value = """
        SELECT p.product_id 
        FROM stock_history sh 
        JOIN product_variants pv ON sh.variant_id = pv.variant_id
        JOIN products p ON pv.product_id = p.product_id
        WHERE sh.type = 'import'
        GROUP BY p.product_id
        ORDER BY MAX(sh.created_at) DESC
        LIMIT 10
    """, nativeQuery = true)
    List<Integer> findTop10RecentlyImportedProductIds();
    
}
