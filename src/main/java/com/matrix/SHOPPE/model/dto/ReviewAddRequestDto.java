package com.matrix.SHOPPE.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewAddRequestDto {
    @NotBlank(message = "Comment must not be blank")
    @Size(min = 3, max = 1000, message = "Comment must be between 3 and 1000 characters")
    private String comment;

    @NotNull(message = "Star rating must not be null")
    @DecimalMin(value = "1.0", message = "Star rating must be at least 1.0")
    @DecimalMax(value = "5.0", message = "Star rating must not exceed 5.0")
    private Double starRating;

    @NotNull(message = "Product ID must not be null")
    @Positive(message = "Product ID must be a positive number")
    private Integer productId;
}