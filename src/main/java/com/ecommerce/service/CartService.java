package com.ecommerce.service;

import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.dto.CartResponse;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartResponse getCart(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        return buildCartResponse(cartItems);
    }

    @Transactional
    public CartResponse addToCart(User user, CartItemRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        Optional<CartItem> existingItem = cartItemRepository.findByUserAndProductIdAndVariant(
                user, request.getProductId(), request.getVariant());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
        } else {
            CartItem newItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(request.getQuantity())
                    .variant(request.getVariant())
                    .build();
            cartItemRepository.save(newItem);
        }

        return getCart(user);
    }

    @Transactional
    public CartResponse updateQuantity(User user, Long productId, String variant, Integer quantity) {
        CartItem cartItem = cartItemRepository.findByUserAndProductIdAndVariant(user, productId, variant)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        return getCart(user);
    }

    @Transactional
    public CartResponse removeFromCart(User user, Long productId, String variant) {
        cartItemRepository.deleteByUserAndProductIdAndVariant(user, productId, variant);
        return getCart(user);
    }

    @Transactional
    public CartResponse clearCart(User user) {
        cartItemRepository.deleteByUser(user);
        return getCart(user);
    }

    private CartResponse buildCartResponse(List<CartItem> cartItems) {
        List<CartResponse.CartItemDto> items = cartItems.stream()
                .map(ci -> CartResponse.CartItemDto.builder()
                        .product(ProductResponse.fromEntity(ci.getProduct()))
                        .quantity(ci.getQuantity())
                        .variant(ci.getVariant())
                        .build())
                .collect(Collectors.toList());

        int totalQuantity = cartItems.stream().mapToInt(CartItem::getQuantity).sum();
        double totalPrice = cartItems.stream()
                .mapToDouble(ci -> ci.getProduct().getPrice() * ci.getQuantity())
                .sum();

        return CartResponse.builder()
                .items(items)
                .totalQuantity(totalQuantity)
                .totalPrice(Math.round(totalPrice * 100.0) / 100.0)
                .build();
    }
}
