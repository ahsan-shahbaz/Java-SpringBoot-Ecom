package com.ecommerce.service;

import com.ecommerce.dto.DashboardStats;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.entity.Order;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public DashboardStats getDashboardStats() {
        Double totalRevenue = orderRepository.getTotalRevenue();
        if (totalRevenue == null) totalRevenue = 0.0;

        long totalOrders = orderRepository.count();
        long totalProducts = productRepository.count();
        long totalUsers = userRepository.count();

        List<Order> recentOrders = orderRepository.findTop5ByOrderByCreatedAtDesc();
        List<OrderResponse> recentOrderResponses = recentOrders.stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());

        return DashboardStats.builder()
                .totalRevenue(totalRevenue)
                .totalOrders(totalOrders)
                .totalProducts(totalProducts)
                .totalUsers(totalUsers)
                .recentOrders(recentOrderResponses)
                .build();
    }

}
