package com.matrix.SHOPPE.mapper;

import com.matrix.SHOPPE.model.dto.ProductAddRequestDto;
import com.matrix.SHOPPE.model.dto.ProductBriefDto;
import com.matrix.SHOPPE.model.dto.ProductDto;
import com.matrix.SHOPPE.model.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto productToProductDto(Product product);

    ProductBriefDto productToProductBriefDto(Product product);

    Product productAddRequestToProduct(ProductAddRequestDto productAddRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateProduct(ProductAddRequestDto productAddRequestDto, @MappingTarget Product product);
}
