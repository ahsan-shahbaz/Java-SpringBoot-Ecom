package com.ecommerce.service;

import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ProductResponse.fromEntity(product);
    }

    public List<String> getCategories() {
        return productRepository.findDistinctCategories();
    }

    public List<ProductResponse> getFeaturedProducts() {
        return productRepository.findFeaturedProducts().stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchProducts(String query) {
        return productRepository.searchProducts(query).stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
