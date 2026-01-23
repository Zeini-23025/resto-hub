package com.restaurant.order.dto;

import com.restaurant.order.model.OrderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    @NotBlank(message = "Customer name is required")
    private String customerName;

    private String customerPhone;

    private Integer tableNumber;

    @NotNull(message = "Order type is required")
    private OrderType orderType;

    private String deliveryAddress;

    @NotEmpty(message = "Order must have at least one item")
    @Valid
    private List<OrderItemRequest> items;
}
