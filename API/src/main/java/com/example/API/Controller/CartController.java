package com.example.API.Controller;

import com.example.API.DTO.Reponse.CartItemResponse;
import com.example.API.DTO.Request.AddToCartRequest;
import com.example.API.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @GetMapping("/my-items")
    public ResponseEntity<List<CartItemResponse>> getCartItemsByUserId(@RequestParam("userId") Integer userId) {
        List<CartItemResponse> cartItems = cartService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }
    @PutMapping("/my-items/quantity")
    public ResponseEntity<String> updateQuantity(
            @RequestParam Integer userId,
            @RequestParam Integer cartItemId,
            @RequestParam Integer quantity) {

        cartService.updateCartItemQuantity(userId, cartItemId, quantity);
        return ResponseEntity.ok("Quantity updated successfully");
    }

    @DeleteMapping("/my-items")
    public ResponseEntity<String> deleteItem(
            @RequestParam Integer userId,
            @RequestParam Integer cartItemId) {

        cartService.deleteCartItem(userId, cartItemId);
        return ResponseEntity.ok("Item deleted successfully");
    }

    @GetMapping("/my-items/total-quantity")
    public ResponseEntity<Integer> getCartTotalQuantity(@RequestParam("userId") Integer userId) {
        int totalQuantity = cartService.getCartTotalQuantity(userId);
        return ResponseEntity.ok(totalQuantity);
    }
    @PostMapping("/my-items")
    public ResponseEntity<String> addToCart(@RequestBody AddToCartRequest request) {
        cartService.addToCart(request);
        return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng");
    }


}
