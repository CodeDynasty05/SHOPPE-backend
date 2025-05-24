package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishlistService {
    void addToWishlist(Integer productId, Integer userId);
    void removeFromWishlist(Integer productId, Integer userId);
    List<ProductDto> getWishlistProducts(Integer userId);
}
