package com.matrix.SHOPPE.mapper;

import com.matrix.SHOPPE.model.dto.CategoryDto;
import com.matrix.SHOPPE.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDTO(Category category);

    Category toCategory(CategoryDto categoryDto);
}
