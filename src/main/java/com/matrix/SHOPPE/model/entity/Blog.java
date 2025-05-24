package com.matrix.SHOPPE.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import javax.xml.stream.events.Comment;
import java.time.LocalDate;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private BlogCategories category;

    @CurrentTimestamp
    @Column(name = "blog_date")
    private LocalDate blogDate;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "image_path")
    private String imagePath;
}