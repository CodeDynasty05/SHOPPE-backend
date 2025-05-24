package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryDto> getCategories();
    CategoryDto addCategory(CategoryDto category);
    CategoryDto updateCategory(CategoryDto category);
    void deleteCategory(Integer id);
}
