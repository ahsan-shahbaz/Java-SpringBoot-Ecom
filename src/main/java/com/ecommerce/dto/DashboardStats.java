package com.ecommerce.dto;

import lombok.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DashboardStats {
    private Double totalRevenue;
    private Long totalOrders;
    private Long totalProducts;
    private Long totalUsers;
    private List<OrderResponse> recentOrders;
}
