package com.restaurant.menu.dto;

import com.restaurant.menu.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MenuItemRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Category is required")
    private Category category;

    private Boolean available;

    private String imageUrl;

    @Positive(message = "Preparation time must be positive")
    private Integer preparationTime;
}
