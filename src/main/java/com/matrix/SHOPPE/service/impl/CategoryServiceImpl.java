package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.CategoryRepository;
import com.matrix.SHOPPE.mapper.CategoryMapper;
import com.matrix.SHOPPE.model.dto.CategoryDto;
import com.matrix.SHOPPE.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDto addCategory(CategoryDto category) {
        return categoryMapper.toCategoryDTO(categoryRepository.save(categoryMapper.toCategory(category)));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto category) {
        return categoryMapper.toCategoryDTO(categoryRepository.save(categoryMapper.toCategory(category)));
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}
