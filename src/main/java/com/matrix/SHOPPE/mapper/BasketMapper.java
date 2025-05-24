package com.matrix.SHOPPE.mapper;

import com.matrix.SHOPPE.model.dto.BasketAddRequestDto;
import com.matrix.SHOPPE.model.dto.BasketDto;
import com.matrix.SHOPPE.model.entity.Basket;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BasketMapper {
    BasketDto basketToBasketDto(Basket basket);

    Basket basketAddRequestToBasket(BasketAddRequestDto basketAddRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Basket updateBasket(BasketAddRequestDto basketAddRequestDto, @MappingTarget Basket basket);
}
