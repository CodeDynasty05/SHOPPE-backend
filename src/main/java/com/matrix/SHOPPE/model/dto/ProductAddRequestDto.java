package com.matrix.SHOPPE.model.dto;

import com.matrix.SHOPPE.model.entity.ProductInfo;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class ProductAddRequestDto {
    @NotBlank(message = "Product name must not be blank")
    private String productName;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0")
    private Double price;

    @NotNull(message = "Stock must not be null")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private Integer stock;

    @DecimalMin(value = "0.0", message = "Discount must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Discount must be less than or equal to 100")
    private Double discount;

    @NotBlank(message = "Description must not be blank")
    private String description;

    private String image;

    @NotNull(message = "Category ID must not be null")
    private Integer categoryId;

    private ProductInfo productInfo;

    @NotEmpty(message = "At least one color must be specified")
    private List<String> colourNames;

    @NotEmpty(message = "At least one material must be specified")
    private List<String> materialNames;
}