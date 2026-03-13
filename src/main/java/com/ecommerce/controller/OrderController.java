package com.ecommerce.controller;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.entity.User;
import com.ecommerce.service.AuthService;
import com.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            Authentication authentication,
            @Valid @RequestBody OrderRequest request) {
        User user = getUser(authentication);
        return ResponseEntity.ok(orderService.placeOrder(user, request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getUserOrders(Authentication authentication) {
        User user = getUser(authentication);
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            Authentication authentication,
            @PathVariable String id) {
        User user = getUser(authentication);
        return ResponseEntity.ok(orderService.getOrderById(user, id));
    }

    private User getUser(Authentication authentication) {
        return authService.getUserByEmail(authentication.getName());
    }
}
