package com.ecommerce.controller;

import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.User;
import com.ecommerce.service.AuthService;
import com.ecommerce.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getWishlist(Authentication authentication) {
        User user = getUser(authentication);
        return ResponseEntity.ok(wishlistService.getWishlist(user));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<List<ProductResponse>> toggleWishlistItem(
            Authentication authentication,
            @PathVariable Long productId) {
        User user = getUser(authentication);
        return ResponseEntity.ok(wishlistService.toggleWishlistItem(user, productId));
    }

    private User getUser(Authentication authentication) {
        return authService.getUserByEmail(authentication.getName());
    }
}
