package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private Double total;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @org.hibernate.annotations.Generated
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "datetime DEFAULT GETDATE()")
    private LocalDateTime createdAt;

    // Shipping info
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zipCode;

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}
