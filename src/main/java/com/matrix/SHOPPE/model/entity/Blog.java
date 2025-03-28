package com.matrix.SHOPPE.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 100)
    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "blog_date")
    private LocalDate blogDate;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "image_path")
    private String imagePath;

}