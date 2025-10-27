package com.example.API.Repository;

import com.example.API.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCart_User_UserId(Integer userId);
    Optional<CartItem> findByCart_CartIdAndVariant_VariantId(Integer cartId, Integer variantId);

    Optional<CartItem> findByIdAndCart_User_UserId(Integer id, Integer userId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.id = :cartItemId AND ci.cart.user.userId = :userId")
    Optional<CartItem> findByIdAndUserId(@Param("cartItemId") int cartItemId, @Param("userId") int userId);

}
