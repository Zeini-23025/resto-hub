package com.restaurant.order.dto;

import com.restaurant.order.model.OrderStatus;
import com.restaurant.order.model.OrderType;
import com.restaurant.order.model.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private String customerName;
    private String customerPhone;
    private Integer tableNumber;
    private OrderType orderType;
    private OrderStatus status;
    private List<OrderItem> items;
    private Double totalAmount;
    private String deliveryAddress;
    private LocalDateTime createdAt;
}
