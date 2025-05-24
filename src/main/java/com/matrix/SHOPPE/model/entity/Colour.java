package com.matrix.SHOPPE.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString
@Table(name="colours")
public class Colour {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String colourName;
    @ManyToMany(mappedBy = "colours")
    @JsonIgnore
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();
}
