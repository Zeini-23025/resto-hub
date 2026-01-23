package com.restaurant.menu.service;

import com.restaurant.menu.dto.MenuItemRequest;
import com.restaurant.menu.dto.MenuItemResponse;
import com.restaurant.menu.exception.MenuItemNotFoundException;
import com.restaurant.menu.model.MenuItem;
import com.restaurant.menu.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository repository;

    @Override
    public List<MenuItemResponse> getAllItems() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemResponse getItemById(Long id) {
        MenuItem item = repository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Menu item not found with id: " + id));
        return mapToResponse(item);
    }

    @Override
    public MenuItemResponse createItem(MenuItemRequest request) {
        MenuItem item = new MenuItem();
        BeanUtils.copyProperties(request, item);
        MenuItem savedItem = repository.save(item);
        return mapToResponse(savedItem);
    }

    @Override
    public MenuItemResponse updateItem(Long id, MenuItemRequest request) {
        MenuItem item = repository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Menu item not found with id: " + id));

        BeanUtils.copyProperties(request, item);
        item.setId(id); // Ensure ID remains the same
        MenuItem updatedItem = repository.save(item);
        return mapToResponse(updatedItem);
    }

    @Override
    public void deleteItem(Long id) {
        if (!repository.existsById(id)) {
            throw new MenuItemNotFoundException("Menu item not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public MenuItemResponse updateAvailability(Long id, Boolean available) {
        MenuItem item = repository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Menu item not found with id: " + id));
        item.setAvailable(available);
        MenuItem updatedItem = repository.save(item);
        return mapToResponse(updatedItem);
    }

    private MenuItemResponse mapToResponse(MenuItem item) {
        MenuItemResponse response = new MenuItemResponse();
        BeanUtils.copyProperties(item, response);
        return response;
    }
}
