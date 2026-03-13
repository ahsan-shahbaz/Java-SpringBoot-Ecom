package com.ecommerce.service;

import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.entity.WishlistItem;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.WishlistItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;

    public List<ProductResponse> getWishlist(User user) {
        return wishlistItemRepository.findByUser(user).stream()
                .map(wi -> ProductResponse.fromEntity(wi.getProduct()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProductResponse> toggleWishlistItem(User user, Long productId) {
        Optional<WishlistItem> existing = wishlistItemRepository.findByUserAndProductId(user, productId);

        if (existing.isPresent()) {
            wishlistItemRepository.delete(existing.get());
        } else {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

            WishlistItem item = WishlistItem.builder()
                    .user(user)
                    .product(product)
                    .build();
            wishlistItemRepository.save(item);
        }

        return getWishlist(user);
    }
}
