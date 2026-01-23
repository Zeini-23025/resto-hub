package com.restaurant.kitchen.service;

import com.restaurant.kitchen.dto.OrderEvent;
import com.restaurant.kitchen.model.KitchenOrder;
import com.restaurant.kitchen.model.KitchenOrderItem;
import com.restaurant.kitchen.model.KitchenOrderStatus;
import com.restaurant.kitchen.repository.KitchenOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class KitchenService {

    private final KitchenOrderRepository repository;

    public void createOrder(OrderEvent event) {
        log.info("Received order event: {}", event);

        KitchenOrder kitchenOrder = new KitchenOrder();
        kitchenOrder.setOrderId(event.getOrderId());
        kitchenOrder.setStatus(KitchenOrderStatus.PENDING); // Initial status in kitchen
        kitchenOrder.setReceivedAt(LocalDateTime.now());

        List<KitchenOrderItem> items = event.getItems().stream().map(itemDto -> {
            KitchenOrderItem item = new KitchenOrderItem();
            item.setMenuItemName(itemDto.getMenuItemName());
            item.setQuantity(itemDto.getQuantity());
            item.setKitchenOrder(kitchenOrder);
            return item;
        }).collect(Collectors.toList());

        kitchenOrder.setItems(items);

        repository.save(kitchenOrder);
        log.info("Saved kitchen order with ID: {}", kitchenOrder.getId());
    }

    public List<KitchenOrder> getQueue() {
        return repository.findByStatusNot(KitchenOrderStatus.SERVED);
    }

    public KitchenOrder startOrder(Long id) {
        KitchenOrder order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setStatus(KitchenOrderStatus.PREPARING);
        return repository.save(order);
    }

    public KitchenOrder completeOrder(Long id) {
        KitchenOrder order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setStatus(KitchenOrderStatus.READY);
        return repository.save(order);
    }

    public List<KitchenOrder> getAllOrders() {
        return repository.findAll();
    }
}
