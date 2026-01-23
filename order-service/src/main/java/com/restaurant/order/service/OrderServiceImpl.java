package com.restaurant.order.service;

import com.restaurant.order.client.MenuServiceClient;
import com.restaurant.order.dto.MenuItemResponse;
import com.restaurant.order.dto.OrderItemRequest;
import com.restaurant.order.dto.OrderRequest;
import com.restaurant.order.dto.OrderResponse;
import com.restaurant.order.model.Order;
import com.restaurant.order.model.OrderItem;
import com.restaurant.order.model.OrderStatus;
import com.restaurant.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final MenuServiceClient menuServiceClient;
    private final com.restaurant.order.client.KitchenServiceClient kitchenServiceClient;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order();
        BeanUtils.copyProperties(request, order);
        order.setStatus(OrderStatus.PENDING);

        double totalAmount = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {
            MenuItemResponse menuItem = menuServiceClient.getMenuItem(itemRequest.getMenuItemId());

            if (menuItem == null) {
                throw new RuntimeException("Menu item not found: " + itemRequest.getMenuItemId());
            }
            if (!Boolean.TRUE.equals(menuItem.getAvailable())) {
                throw new RuntimeException("Menu item not available: " + menuItem.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItemId(menuItem.getId());
            orderItem.setMenuItemName(menuItem.getName());
            orderItem.setPrice(menuItem.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setOrder(order);

            totalAmount += menuItem.getPrice() * itemRequest.getQuantity();
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = repository.save(order);

        // Call Kitchen Service
        Map<String, Object> orderEvent = new HashMap<>();
        orderEvent.put("orderId", savedOrder.getId());
        orderEvent.put("items", savedOrder.getItems());
        orderEvent.put("status", savedOrder.getStatus());
        orderEvent.put("timestamp", LocalDateTime.now().toString());

        try {
            kitchenServiceClient.createOrder(orderEvent);
            log.info("Order sent to kitchen service: {}", savedOrder.getId());
        } catch (Exception e) {
            log.error("Failed to send order to kitchen", e);
            // Optionally rollback or mark as ERROR
        }

        return mapToResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToResponse(order);
    }

    @Override
    public OrderResponse updateStatus(Long id, OrderStatus status) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return mapToResponse(repository.save(order));
    }

    private OrderResponse mapToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        BeanUtils.copyProperties(order, response);
        return response;
    }
}
