package com.matrix.SHOPPE.controller;


import com.matrix.SHOPPE.model.dto.BasketAddRequestDto;
import com.matrix.SHOPPE.model.dto.BasketDto;
import com.matrix.SHOPPE.service.BasketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {
    private final BasketService basketService;

    @GetMapping("/{userId}")
    public List<BasketDto> getBasket(@PathVariable Integer userId) {
        return basketService.getBasketById(userId);
    }

    @PostMapping
    public BasketDto addBasket(@Valid @RequestBody BasketAddRequestDto basketAddRequestDto) {
        return basketService.addBasket(basketAddRequestDto);
    }

    @PutMapping("/{id}")
    public BasketDto updateQuantity(@PathVariable Integer id, @RequestParam Integer quantity) {
        return basketService.updateQuantity(id, quantity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        basketService.delete(id);
    }
}
