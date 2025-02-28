package com.matrix.SHOPPE.controller;

import com.matrix.SHOPPE.model.Category;
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
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }

    @PutMapping
    public void updateCategory(@RequestBody Category user) {
        categoryService.updateCategory(user);
    }

    @PostMapping
    public void addCategory(@RequestBody Category user) {
        categoryService.addCategory(user);
    }
}
