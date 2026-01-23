package com.restaurant.kitchen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "kitchen_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KitchenOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId; // Reference to Order Service ID

    @Enumerated(EnumType.STRING)
    private KitchenOrderStatus status;

    private LocalDateTime receivedAt;

    @OneToMany(mappedBy = "kitchenOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KitchenOrderItem> items;
}
