package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.DTO.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryDTO> getCategories();
    CategoryDTO addCategory(CategoryDTO category);
    CategoryDTO updateCategory(CategoryDTO category);
    void deleteCategory(Integer id);
}
