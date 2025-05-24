package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.BasketRepository;
import com.matrix.SHOPPE.Repository.ProductRepository;
import com.matrix.SHOPPE.Repository.UserRepository;
import com.matrix.SHOPPE.exception.BasketNotFoundException;
import com.matrix.SHOPPE.exception.ProductNotFoundException;
import com.matrix.SHOPPE.exception.UserNotFoundException;
import com.matrix.SHOPPE.mapper.BasketMapper;
import com.matrix.SHOPPE.model.dto.BasketAddRequestDto;
import com.matrix.SHOPPE.model.dto.BasketDto;
import com.matrix.SHOPPE.model.entity.Basket;
import com.matrix.SHOPPE.model.entity.Product;
import com.matrix.SHOPPE.model.entity.User;
import com.matrix.SHOPPE.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BasketMapper basketMapper;

    @Override
    public List<BasketDto> getBasketById(Integer userId) {
        return basketRepository.findByUserId(userId).stream().map(basketMapper::basketToBasketDto).collect(Collectors.toList());
    }

    @Override
    public BasketDto addBasket(BasketAddRequestDto basketAddRequestDto) {
        Product product = productRepository.findById(basketAddRequestDto.getProductId()).orElseThrow(()-> new ProductNotFoundException("Product with id "+basketAddRequestDto.getProductId()+" not found"));
        User user = userRepository.findById(basketAddRequestDto.getUserId()).orElseThrow(()-> new UserNotFoundException("User with id "+basketAddRequestDto.getUserId()+" not found"));
        Basket basket = new Basket();
        basket.setProduct(product);
        basket.setQuantity(basketAddRequestDto.getQuantity());
        basket.setUser(user);
        return basketMapper.basketToBasketDto(basketRepository.save(basket));
    }

    @Override
    public BasketDto updateQuantity(Integer id, Integer quantity) {
        Basket basket = basketRepository.findById(id).orElseThrow(()-> new BasketNotFoundException("Basket with id "+id+" not found"));
        basket.setQuantity(quantity);
        return basketMapper.basketToBasketDto(basketRepository.save(basket));
    }

    @Override
    public void delete(Integer id) {
        if (!basketRepository.existsById(id)) {
            throw new BasketNotFoundException("Basket with id "+id+" not found");
        }
        basketRepository.deleteById(id);
    }
}
