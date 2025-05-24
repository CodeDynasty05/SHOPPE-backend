package com.matrix.SHOPPE.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.matrix.SHOPPE.model.entity.Category;
import com.matrix.SHOPPE.model.entity.Colour;
import com.matrix.SHOPPE.model.entity.Material;
import com.matrix.SHOPPE.model.entity.ProductInfo;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class ProductDto {
    private Integer id;
    private String productName;
    private Double price;
    private Integer stock;
    private Double discount;
    private Double rating;
    private String description;
    private String image;
    private Category category;
    private ProductInfo productInfo;
    private List<Colour> colours;
    private List<Material> materials;
}
