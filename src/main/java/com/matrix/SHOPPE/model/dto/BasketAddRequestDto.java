package com.matrix.SHOPPE.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BasketAddRequestDto {
    @NotNull(message = "User ID must not be null")
    private Integer userId;

    @NotNull(message = "Product ID must not be null")
    private Integer productId;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}