package com.ecommerce.dto;

import com.ecommerce.entity.Product;
import lombok.*;
import java.util.List;

/**
 * Response DTO that matches the Angular Product interface exactly.
 * Nests rating as { rate, count } object.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String title;
    private Double price;
    private Double originalPrice;
    private Double discountPercentage;
    private String description;
    private String category;
    private String brand;
    private String image;
    private List<String> images;
    private List<String> features;
    private Integer stock;
    private Rating rating;
    private List<String> tags;

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class Rating {
        private Double rate;
        private Integer count;
    }

    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .originalPrice(product.getOriginalPrice())
                .discountPercentage(product.getDiscountPercentage())
                .description(product.getDescription())
                .category(product.getCategory())
                .brand(product.getBrand())
                .image(product.getImage())
                .images(product.getImages())
                .features(product.getFeatures())
                .stock(product.getStock())
                .rating(Rating.builder()
                        .rate(product.getRatingRate())
                        .count(product.getRatingCount())
                        .build())
                .tags(product.getTags())
                .build();
    }
}
