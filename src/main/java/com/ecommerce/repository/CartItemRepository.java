package com.ecommerce.repository;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndProductIdAndVariant(User user, Long productId, String variant);

    void deleteByUser(User user);

    void deleteByUserAndProductIdAndVariant(User user, Long productId, String variant);
}
