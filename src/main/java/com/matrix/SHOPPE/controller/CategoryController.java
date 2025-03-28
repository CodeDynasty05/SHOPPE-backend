package com.matrix.SHOPPE.controller;

import com.matrix.SHOPPE.model.DTO.CategoryDTO;
import com.matrix.SHOPPE.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getCategories() {
        return categoryService.getCategories();
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }

    @PutMapping
    public CategoryDTO updateCategory(@RequestBody CategoryDTO category) {
        return categoryService.updateCategory(category);
    }

    @PostMapping
    public CategoryDTO addCategory(@RequestBody CategoryDTO category) {
        return categoryService.addCategory(category);
    }
}
