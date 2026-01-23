package com.restaurant.order.client;

import com.restaurant.order.dto.MenuItemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "menu-service", url = "${feign.client.config.menu-service.url}")
public interface MenuServiceClient {

    @GetMapping("/api/menu/items/{id}")
    MenuItemResponse getMenuItem(@PathVariable Long id);

    @GetMapping("/api/menu/items")
    List<MenuItemResponse> getAllMenuItems();
}
