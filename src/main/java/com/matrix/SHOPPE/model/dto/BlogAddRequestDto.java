package com.matrix.SHOPPE.model.dto;

import com.matrix.SHOPPE.model.entity.BlogCategories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogAddRequestDto {
    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotNull(message = "Category must not be null")
    private BlogCategories category;

    private String imagePath;
}
