package com.ecommerce.service;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.entity.*;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public OrderResponse placeOrder(User user, OrderRequest request) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty. Cannot place order.");
        }

        double total = cartItems.stream()
                .mapToDouble(ci -> ci.getProduct().getPrice() * ci.getQuantity())
                .sum();

        Order order = Order.builder()
                .user(user)
                .total(Math.round(total * 100.0) / 100.0)
                .status(OrderStatus.PENDING)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .city(request.getCity())
                .zipCode(request.getZipCode())
                .build();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = OrderItem.builder()
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getProduct().getPrice())
                    .build();
            order.addItem(orderItem);
        }

        order = orderRepository.save(order);

        // Clear cart after order
        cartItemRepository.deleteByUser(user);

        return OrderResponse.fromEntity(order);
    }

    public List<OrderResponse> getUserOrders(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(User user, String orderId) {
        Order order = orderRepository.findByIdAndUser(orderId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return OrderResponse.fromEntity(order);
    }
}
