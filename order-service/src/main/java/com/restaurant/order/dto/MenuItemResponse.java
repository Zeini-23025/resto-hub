package com.restaurant.order.dto;

import lombok.Data;

@Data
public class MenuItemResponse {
    private Long id;
    private String name;
    private Double price;
    private Boolean available;
}
