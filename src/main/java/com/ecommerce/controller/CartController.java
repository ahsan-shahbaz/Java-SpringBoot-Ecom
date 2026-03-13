package com.ecommerce.controller;

import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.dto.CartResponse;
import com.ecommerce.entity.User;
import com.ecommerce.service.AuthService;
import com.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart(Authentication authentication) {
        User user = getUser(authentication);
        return ResponseEntity.ok(cartService.getCart(user));
    }

    @PostMapping
    public ResponseEntity<CartResponse> addToCart(
            Authentication authentication,
            @Valid @RequestBody CartItemRequest request) {
        User user = getUser(authentication);
        return ResponseEntity.ok(cartService.addToCart(user, request));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<CartResponse> updateQuantity(
            Authentication authentication,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        User user = getUser(authentication);
        return ResponseEntity.ok(cartService.updateQuantity(user, productId, quantity));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<CartResponse> removeFromCart(
            Authentication authentication,
            @PathVariable Long productId) {
        User user = getUser(authentication);
        return ResponseEntity.ok(cartService.removeFromCart(user, productId));
    }

    @DeleteMapping
    public ResponseEntity<CartResponse> clearCart(Authentication authentication) {
        User user = getUser(authentication);
        return ResponseEntity.ok(cartService.clearCart(user));
    }

    private User getUser(Authentication authentication) {
        return authService.getUserByEmail(authentication.getName());
    }
}
