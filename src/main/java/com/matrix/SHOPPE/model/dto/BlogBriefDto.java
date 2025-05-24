package com.matrix.SHOPPE.model.dto;

import com.matrix.SHOPPE.model.entity.BlogCategories;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogBriefDto {
    private Integer id;
    private String title;
    private BlogCategories category;
    private LocalDateTime blogDate;
    private String imagePath;
}
