package com.matrix.SHOPPE.controller;

import com.matrix.SHOPPE.model.dto.ProductDto;
import com.matrix.SHOPPE.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping("/{userId}")
    public List<ProductDto> getProducts(@PathVariable Integer userId) {
        return wishlistService.getWishlistProducts(userId);
    }

    @DeleteMapping
    public void removeProduct(@RequestParam Integer productId, @RequestParam Integer userId) {
        wishlistService.removeFromWishlist(productId,userId);
    }

    @PostMapping
    public void addProduct(@RequestParam Integer productId, @RequestParam Integer userId) {
        wishlistService.addToWishlist(productId,userId);
    }
}
