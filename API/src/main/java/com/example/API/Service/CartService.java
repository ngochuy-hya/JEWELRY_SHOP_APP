package com.example.API.Service;

import com.example.API.DTO.Reponse.CartItemResponse;
import com.example.API.DTO.Request.AddToCartRequest;
import com.example.API.Entity.Cart;
import com.example.API.Entity.CartItem;
import com.example.API.Entity.ProductVariant;
import com.example.API.Entity.User;
import com.example.API.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final FlashDealRepository flashDealRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;

    public List<CartItemResponse> getCartItemsByUserId(Integer userId) {
        List<CartItem> items = cartItemRepository.findByCart_User_UserId(userId);

        return items.stream().map(item -> {
            var variant = item.getVariant();
            var product = variant.getProduct();
            double originalPrice = variant.getPrice();

            AtomicReference<Double> actualPriceRef = new AtomicReference<>(originalPrice);

            flashDealRepository.findActiveDealForProduct(product.getProductId(), LocalDateTime.now())
                    .ifPresent(deal -> actualPriceRef.set(deal.getDiscountPrice()));

            double actualPrice = actualPriceRef.get();
            double dealDiscount = 0;
            int discountPercent = 0;

            if (actualPrice < originalPrice) {
                dealDiscount = originalPrice - actualPrice;
                discountPercent = (int) ((dealDiscount / originalPrice) * 100);
            }

            CartItemResponse dto = new CartItemResponse();
            dto.setCartItemId(item.getId());
            dto.setVariantId(variant.getVariantId());
            dto.setSku("V" + variant.getVariantId());
            dto.setProductName(product.getName());
            dto.setImageUrl(product.getMainImage());
            dto.setPrice(actualPrice);
            dto.setOldPrice(originalPrice);
            dto.setDealDiscount(dealDiscount);
            dto.setDiscountPercent(discountPercent);
            dto.setQuantity(item.getQuantity());
            dto.setStock(variant.getStock());

            return dto;
        }).collect(Collectors.toList());
    }

    public void updateCartItemQuantity(int userId, int cartItemId, int quantity) {
        CartItem item = cartItemRepository.findByIdAndUserId(cartItemId, userId)
                .orElseThrow(() -> new RuntimeException("Cart item not found or not owned by user"));

        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }


    public void deleteCartItem( Integer userId,Integer cartItemId) {
        CartItem item = cartItemRepository.findByIdAndCart_User_UserId(cartItemId, userId)
                .orElseThrow(() -> new RuntimeException("Cart item not found or not owned by user"));

        cartItemRepository.delete(item);
    }


    public int getCartTotalQuantity(Integer userId) {
        List<CartItem> items = cartItemRepository.findByCart_User_UserId(userId);

        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
    public void addToCart(AddToCartRequest request) {
        // 1. Tìm user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        // 2. Tìm hoặc tạo cart
        Cart cart = cartRepository.findByUser_UserId(user.getUserId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        // 3. Kiểm tra variant có tồn tại không
        ProductVariant variant = productVariantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new RuntimeException("Biến thể sản phẩm không tồn tại"));

        // 4. Tìm cartItem nếu đã tồn tại
        Optional<CartItem> existingItemOpt = cartItemRepository
                .findByCart_CartIdAndVariant_VariantId(cart.getCartId(), variant.getVariantId());

        int currentQuantityInCart = existingItemOpt.map(CartItem::getQuantity).orElse(0);
        int totalRequested = currentQuantityInCart + request.getQuantity();

        // 5. Kiểm tra tồn kho
        if (totalRequested > variant.getStock()) {
            throw new RuntimeException("Số lượng vượt quá tồn kho hiện tại (" + variant.getStock() + ")");
        }

        // 6. Thêm hoặc cập nhật cartItem
        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(totalRequested);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setVariant(variant);
            newItem.setQuantity(request.getQuantity());
            cartItemRepository.save(newItem);
        }
    }


}
