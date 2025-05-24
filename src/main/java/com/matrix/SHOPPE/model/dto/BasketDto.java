package com.matrix.SHOPPE.model.dto;

import com.matrix.SHOPPE.model.entity.Product;
import lombok.Data;

@Data
public class BasketDto {
    private Integer id;
    private Product product;
    private Integer quantity;
}
