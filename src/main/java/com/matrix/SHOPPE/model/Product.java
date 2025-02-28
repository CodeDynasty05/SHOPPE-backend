package com.matrix.SHOPPE.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String productName;
    private Double price;
    private Integer stock;
    private Double discount;
    private Double rating;
    private String description;
    private String image;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;
    @OneToOne(mappedBy = "product")
    private ProductInfo productInfo;
    @ManyToMany(mappedBy = "products")
    private List<Colour> colours;
    @ManyToMany(mappedBy = "products")
    private List<Material> materials;
}
