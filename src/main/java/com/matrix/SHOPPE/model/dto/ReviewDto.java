package com.matrix.SHOPPE.model.dto;

import com.matrix.SHOPPE.model.entity.User;
import lombok.Data;

@Data
public class ReviewDto {
    private Integer id;
    private String comment;
    private String reviewDate;
    private Double starRating;
    private UserDto user;
}
