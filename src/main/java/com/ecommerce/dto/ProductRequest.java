package com.ecommerce.dto;

import lombok.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductRequest {
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
    private List<String> tags;
    private List<String> variants;
}
