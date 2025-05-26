package com.matrix.SHOPPE.mapper;

import com.matrix.SHOPPE.model.dto.OrderDto;
import com.matrix.SHOPPE.model.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto orderToOrderDto(Order order);
}
