package com.example.API.Repository;

import com.example.API.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser_UserIdOrderByCreatedAtDesc(Integer userId);
    Optional<Order> findByOrderIdAndUser_UserId(Integer orderId, Integer userId);
    @Query("SELECT COUNT(oi) > 0 FROM Order o JOIN o.items oi WHERE o.user.userId = :userId AND oi.variant.product.productId = :productId")
    boolean hasUserBoughtProduct(@Param("userId") Integer userId, @Param("productId") Integer productId);

}
