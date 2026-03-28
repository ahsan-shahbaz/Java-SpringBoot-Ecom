package com.ecommerce.repository;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUserOrderByCreatedAtDesc(User user);

    Optional<Order> findByIdAndUser(String id, User user);

    @Query("SELECT SUM(o.total) FROM Order o WHERE o.status != 'CANCELLED'")
    Double getTotalRevenue();

    List<Order> findTop5ByOrderByCreatedAtDesc();
}
