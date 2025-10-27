package com.example.API.Service;

import com.example.API.DTO.Reponse.ProductResponse;
import com.example.API.Entity.Favorite;
import com.example.API.Entity.Product;
import com.example.API.Entity.User;
import com.example.API.Repository.FavoriteRepository;
import com.example.API.Repository.ProductRepository;
import com.example.API.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public void addFavorite(Integer userId, Integer productId) {
        boolean exists = favoriteRepository.existsByUser_UserIdAndProduct_ProductId(userId, productId);
        System.out.println("Favorite exists: " + exists);

        if (exists) {
            return;
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);

        favoriteRepository.save(favorite);
        System.out.println("Favorite saved: " + favorite);
    }

    @Transactional
    public void removeFavorite(Integer userId, Integer productId) {
        favoriteRepository.deleteByUser_UserIdAndProduct_ProductId(userId, productId);
    }
    public List<ProductResponse> getFavoritesByUserId(Integer userId) {
        return favoriteRepository.findFavoriteProductsByUserId(userId);
    }
}
