package com.matrix.SHOPPE.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
@Table(name="materials")
public class Material {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String materialName;
    @ManyToMany(mappedBy = "materials")
    @JsonIgnore
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();
}
