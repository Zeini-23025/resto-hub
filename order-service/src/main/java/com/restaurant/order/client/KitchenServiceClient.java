package com.restaurant.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "kitchen-service", url = "${feign.client.config.kitchen-service.url}")
public interface KitchenServiceClient {

    @PostMapping("/api/kitchen/orders")
    void createOrder(@RequestBody Map<String, Object> orderEvent);
}
