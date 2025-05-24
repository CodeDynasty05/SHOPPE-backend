package com.matrix.SHOPPE.model.dto;

import com.matrix.SHOPPE.model.entity.BlogCategories;
import lombok.Data;

@Data
public class BlogAddRequestDto {
    private String title;
    private String description;
    private BlogCategories category;
    private String imagePath;
}
