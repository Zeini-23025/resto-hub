package com.restaurant.order.service;

import com.restaurant.order.dto.OrderRequest;
import com.restaurant.order.dto.OrderResponse;
import com.restaurant.order.model.OrderStatus;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrderById(Long id);

    OrderResponse updateStatus(Long id, OrderStatus status);
}
