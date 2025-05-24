package com.matrix.SHOPPE.controller;

import com.matrix.SHOPPE.model.dto.CategoryDto;
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
    public List<CategoryDto> getCategories() {
        return categoryService.getCategories();
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }

    @PutMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto category) {
        return categoryService.updateCategory(category);
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody CategoryDto category) {
        return categoryService.addCategory(category);
    }
}
