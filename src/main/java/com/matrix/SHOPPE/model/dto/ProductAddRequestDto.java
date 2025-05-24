package com.matrix.SHOPPE.model.dto;

import com.matrix.SHOPPE.model.entity.Category;
import com.matrix.SHOPPE.model.entity.Colour;
import com.matrix.SHOPPE.model.entity.Material;
import com.matrix.SHOPPE.model.entity.ProductInfo;
import lombok.Data;

import java.util.List;

@Data
public class ProductAddRequestDto {
    private String productName;
    private Double price;
    private Integer stock;
    private Double discount;
    private String description;
    private String image;
    private Integer categoryId;
    private ProductInfo productInfo;
    private List<String> colourNames;
    private List<String> materialNames;
}
