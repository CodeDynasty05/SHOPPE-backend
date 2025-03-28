package com.matrix.SHOPPE.mapper;

import com.matrix.SHOPPE.model.DTO.CategoryDTO;
import com.matrix.SHOPPE.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toCategoryDTO(Category category);

    Category toCategory(CategoryDTO categoryDTO);
}
