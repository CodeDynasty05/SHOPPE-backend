package com.matrix.SHOPPE.model.dto;

import lombok.Data;

@Data
public class BasketAddRequestDto {
    private Integer userId;
    private Integer productId;
    private Integer quantity;
}
