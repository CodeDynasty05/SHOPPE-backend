package com.matrix.SHOPPE.model.dto;

import com.matrix.SHOPPE.model.entity.Product;
import com.matrix.SHOPPE.model.entity.User;
import lombok.Data;

@Data
public class ReviewAddRequestDto {
    private String comment;
    private Double starRating;
    private Integer userId;
    private Integer productId;
}
