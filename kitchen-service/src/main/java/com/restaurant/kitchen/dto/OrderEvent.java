package com.restaurant.kitchen.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderEvent {
    private Long orderId;
    private List<OrderItemEvent> items;
    private String status;
    private String timestamp;
}
