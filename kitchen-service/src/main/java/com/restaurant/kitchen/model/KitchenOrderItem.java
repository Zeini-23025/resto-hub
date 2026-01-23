package com.restaurant.kitchen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kitchen_order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KitchenOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String menuItemName;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "kitchen_order_id")
    @JsonIgnore
    private KitchenOrder kitchenOrder;
}
