package com.matrix.SHOPPE.model.dto;

import com.matrix.SHOPPE.model.entity.BlogCategories;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BlogDto {
    private Integer id;
    private String title;
    private BlogCategories category;
    private LocalDateTime blogDate;
    private String description;
    private String imagePath;
}
