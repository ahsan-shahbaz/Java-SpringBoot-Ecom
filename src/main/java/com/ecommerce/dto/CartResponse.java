package com.ecommerce.dto;

import lombok.*;
import java.util.List;

/**
 * Matches Angular CartState: { items: CartItem[], totalQuantity, totalPrice }
 * Each CartItem contains { product: Product, quantity: number }
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CartResponse {
    private List<CartItemDto> items;
    private Integer totalQuantity;
    private Double totalPrice;

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class CartItemDto {
        private ProductResponse product;
        private Integer quantity;
        private String variant;
    }
}
