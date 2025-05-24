package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.dto.BasketAddRequestDto;
import com.matrix.SHOPPE.model.dto.BasketDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BasketService {

    List<BasketDto> getBasketById(Integer userId);

    BasketDto addBasket(BasketAddRequestDto basketAddRequestDto);

    BasketDto updateQuantity(Integer id, Integer quantity);

   void delete(Integer id);
}
