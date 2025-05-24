package com.matrix.SHOPPE.mapper;

import com.matrix.SHOPPE.model.dto.BlogAddRequestDto;
import com.matrix.SHOPPE.model.dto.BlogBriefDto;
import com.matrix.SHOPPE.model.dto.BlogDto;
import com.matrix.SHOPPE.model.dto.ProductBriefDto;
import com.matrix.SHOPPE.model.entity.Blog;
import com.matrix.SHOPPE.model.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    BlogDto blogToBlogDto(Blog blog);

    BlogBriefDto productToBlogBriefDto(Blog product);

    Blog blogAddRequestToBlog(BlogAddRequestDto blogAddRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Blog updateBlog(BlogAddRequestDto blogAddRequestDto, @MappingTarget Blog blog);
}
