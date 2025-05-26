package com.matrix.SHOPPE.model.dto;

import lombok.Data;

@Data
public class ReviewAddRequestDto {
    private String comment;
    private Double starRating;
    private Integer productId;
}
