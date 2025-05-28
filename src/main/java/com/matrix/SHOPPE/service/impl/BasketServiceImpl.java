package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.exception.BasketNotFoundException;
import com.matrix.SHOPPE.exception.ProductNotFoundException;
import com.matrix.SHOPPE.exception.UserNotFoundException;
import com.matrix.SHOPPE.mapper.BasketMapper;
import com.matrix.SHOPPE.model.dto.BasketAddRequestDto;
import com.matrix.SHOPPE.model.dto.BasketDto;
import com.matrix.SHOPPE.model.entity.Basket;
import com.matrix.SHOPPE.model.entity.Product;
import com.matrix.SHOPPE.model.entity.User;
import com.matrix.SHOPPE.repository.BasketRepository;
import com.matrix.SHOPPE.repository.ProductRepository;
import com.matrix.SHOPPE.repository.UserRepository;
import com.matrix.SHOPPE.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BasketMapper basketMapper;

    @Override
    public List<BasketDto> getBasketById(Integer userId) {
        log.info("Fetching basket for user ID: {}", userId);
        List<Basket> baskets = basketRepository.findByUserId(userId);
        return baskets.stream().map(basketMapper::basketToBasketDto).toList();
    }

    @Override
    public BasketDto addBasket(BasketAddRequestDto basketAddRequestDto) {
        log.info("Adding item to basket for user ID: {}", basketAddRequestDto.getUserId());
        Product product = productRepository.findById(basketAddRequestDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        User user = userRepository.findById(basketAddRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Basket basket = new Basket();
        basket.setProduct(product);
        basket.setQuantity(basketAddRequestDto.getQuantity());
        basket.setUser(user);

        Basket savedBasket = basketRepository.save(basket);
        log.info("Added item to basket with ID: {}", savedBasket.getId());
        return basketMapper.basketToBasketDto(savedBasket);
    }

    @Override
    public BasketDto updateQuantity(Integer id, Integer quantity) {
        log.info("Updating basket item quantity, ID: {}, quantity: {}", id, quantity);
        Basket basket = basketRepository.findById(id)
                .orElseThrow(() -> new BasketNotFoundException("Basket not found"));
        basket.setQuantity(quantity);
        Basket updatedBasket = basketRepository.save(basket);
        log.info("Updated basket item quantity, ID: {}", updatedBasket.getId());
        return basketMapper.basketToBasketDto(updatedBasket);
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting basket item with ID: {}", id);
        basketRepository.deleteById(id);
        log.info("Deleted basket item with ID: {}", id);
    }
}