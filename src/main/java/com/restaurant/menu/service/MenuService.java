package com.restaurant.menu.service;

import com.restaurant.menu.exception.ResourceNotFoundException;
import com.restaurant.menu.model.MenuItem;
import com.restaurant.menu.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuItem> getAllItems() {
        return menuRepository.findAll();
    }

    public MenuItem createItem(MenuItem item) {
        return menuRepository.save(item);
    }

    public MenuItem getItemById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu item not found with id " + id));
    }

    public MenuItem updateItem(Long id, MenuItem updatedItem) {
        MenuItem existing = getItemById(id);

        existing.setName(updatedItem.getName());
        existing.setDescription(updatedItem.getDescription());
        existing.setPrice(updatedItem.getPrice());
        existing.setAvailable(updatedItem.isAvailable());

        return menuRepository.save(existing);
    }

    public void deleteItem(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu item not found with id " + id);
        }
        menuRepository.deleteById(id);
    }
}
