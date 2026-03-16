package com.ecommerce.service;

import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts(Double priceMin, Double priceMax, String category, String brand, Double rating, Boolean inStock, String sortBy) {
        Specification<Product> spec = Specification.where(null);

        if (priceMin != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), priceMin));
        }
        if (priceMax != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), priceMax));
        }
        if (category != null && !category.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category"), category));
        }
        if (brand != null && !brand.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("brand"), brand));
        }
        if (rating != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("ratingRate"), rating));
        }
        if (inStock != null && inStock) {
            spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("stock"), 0));
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "id"); // default
        if (sortBy != null) {
            switch (sortBy) {
                case "price_asc":
                    sort = Sort.by(Sort.Direction.ASC, "price");
                    break;
                case "price_desc":
                    sort = Sort.by(Sort.Direction.DESC, "price");
                    break;
                case "popularity":
                    sort = Sort.by(Sort.Direction.DESC, "ratingCount");
                    break;
                case "rating":
                    sort = Sort.by(Sort.Direction.DESC, "ratingRate");
                    break;
                case "newest":
                    sort = Sort.by(Sort.Direction.DESC, "id");
                    break;
            }
        }

        return productRepository.findAll(spec, sort).stream()
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
