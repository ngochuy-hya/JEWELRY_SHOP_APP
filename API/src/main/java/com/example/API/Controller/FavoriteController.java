package com.example.API.Controller;

import com.example.API.DTO.Reponse.ProductResponse;
import com.example.API.Service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/add")
    public ResponseEntity<Void> addFavorite(@RequestParam Integer userId, @RequestParam Integer productId) {
        favoriteService.addFavorite(userId, productId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFavorite(@RequestParam Integer userId, @RequestParam Integer productId) {
        favoriteService.removeFavorite(userId, productId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<ProductResponse>> getFavorites(@PathVariable Integer userId) {
        List<ProductResponse> favorites = favoriteService.getFavoritesByUserId(userId);
        return ResponseEntity.ok(favorites);
    }
}

