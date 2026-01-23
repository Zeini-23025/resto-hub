package com.restaurant.menu.service;

import com.restaurant.menu.dto.MenuItemRequest;
import com.restaurant.menu.dto.MenuItemResponse;

import java.util.List;

public interface MenuService {
    List<MenuItemResponse> getAllItems();

    MenuItemResponse getItemById(Long id);

    MenuItemResponse createItem(MenuItemRequest request);

    MenuItemResponse updateItem(Long id, MenuItemRequest request);

    void deleteItem(Long id);

    MenuItemResponse updateAvailability(Long id, Boolean available);
}
