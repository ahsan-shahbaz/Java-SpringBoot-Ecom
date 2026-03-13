package com.ecommerce.repository;

import com.ecommerce.entity.WishlistItem;
import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByUser(User user);

    Optional<WishlistItem> findByUserAndProductId(User user, Long productId);

    boolean existsByUserAndProductId(User user, Long productId);
}
