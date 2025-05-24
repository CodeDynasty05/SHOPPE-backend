package com.matrix.SHOPPE.model.dto;

import com.matrix.SHOPPE.model.entity.Category;
import com.matrix.SHOPPE.model.entity.Colour;
import com.matrix.SHOPPE.model.entity.Material;
import com.matrix.SHOPPE.model.entity.ProductInfo;
import lombok.Data;

import java.util.List;

@Data
public class ProductBriefDto {
    private Integer id;
    private String productName;
    private Double price;
    private Integer stock;
    private Double discount;
    private Double rating;
    private String image;
    private Category category;
}
