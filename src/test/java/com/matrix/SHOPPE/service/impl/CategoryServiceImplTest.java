package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.CategoryRepository;
import com.matrix.SHOPPE.mapper.CategoryMapper;
import com.matrix.SHOPPE.model.DTO.CategoryDTO;
import com.matrix.SHOPPE.model.entity.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1);
        category.setCategoryName("Electronics");

        categoryDTO = new CategoryDTO();
        categoryDTO.setId(1);
        categoryDTO.setCategoryName("Electronics");
    }

    @Test
    void testGetCategories() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));
        when(categoryMapper.toCategoryDTO(category)).thenReturn(categoryDTO);

        List<CategoryDTO> categories = categoryService.getCategories();

        assertEquals(1, categories.size());
        assertEquals("Electronics", categories.get(0).getCategoryName());
        verify(categoryRepository, times(1)).findAll();
    }

    @AfterEach
    void tearDown() {
        category=null;
        categoryDTO=null;
    }
}