package com.ecommerce.dto;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Matches Angular Order: { id, userId, items, total, status, date }
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class OrderResponse {
    private String id;
    private Long userId;
    private List<OrderItemDto> items;
    private Double total;
    private String status;
    private LocalDateTime date;

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class OrderItemDto {
        private ProductResponse product;
        private Integer quantity;
        private Double price;
    }

    public static OrderResponse fromEntity(Order order) {
        List<OrderItemDto> itemDtos = order.getItems().stream()
                .map(item -> OrderItemDto.builder()
                        .product(ProductResponse.fromEntity(item.getProduct()))
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .items(itemDtos)
                .total(order.getTotal())
                .status(order.getStatus().name())
                .date(order.getCreatedAt())
                .build();
    }
}
