package com.restaurant.kitchen.controller;

import com.restaurant.kitchen.model.KitchenOrder;
import com.restaurant.kitchen.model.KitchenOrderStatus;
import com.restaurant.kitchen.service.KitchenService;
import com.restaurant.kitchen.repository.KitchenOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kitchen/orders")
@RequiredArgsConstructor
public class KitchenController {

    private final KitchenService service;
    private final KitchenOrderRepository repository;

    @GetMapping
    public List<KitchenOrder> getAllOrders() {
        return service.getAllOrders();
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody com.restaurant.kitchen.dto.OrderEvent event) {
        service.createOrder(event);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/queue")
    public List<KitchenOrder> getQueue() {
        return service.getQueue();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<KitchenOrder> startOrder(@PathVariable Long id) {
        return ResponseEntity.ok(service.startOrder(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<KitchenOrder> completeOrder(@PathVariable Long id) {
        return ResponseEntity.ok(service.completeOrder(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<KitchenOrder> updateStatus(@PathVariable Long id, @RequestParam KitchenOrderStatus status) {
        return repository.findById(id)
                .map(order -> {
                    order.setStatus(status);
                    return ResponseEntity.ok(repository.save(order));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
