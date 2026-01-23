package com.restaurant.kitchen.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemEvent {
    private Long id;
    private String menuItemName;
    private Double price;
    private Integer quantity;
}
