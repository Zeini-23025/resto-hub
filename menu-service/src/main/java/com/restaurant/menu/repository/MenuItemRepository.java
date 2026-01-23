package com.restaurant.menu.repository;

import com.restaurant.menu.model.Category;
import com.restaurant.menu.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategory(Category category);

    List<MenuItem> findByAvailableTrue();
}
