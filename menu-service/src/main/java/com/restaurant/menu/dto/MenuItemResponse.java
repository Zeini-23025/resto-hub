package com.restaurant.menu.dto;

import com.restaurant.menu.model.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MenuItemResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Category category;
    private Boolean available;
    private String imageUrl;
    private Integer preparationTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
