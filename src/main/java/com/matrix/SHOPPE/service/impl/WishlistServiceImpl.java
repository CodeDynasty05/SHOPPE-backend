package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.ProductRepository;
import com.matrix.SHOPPE.Repository.UserRepository;
import com.matrix.SHOPPE.exception.ProductNotFoundException;
import com.matrix.SHOPPE.exception.UserNotFoundException;
import com.matrix.SHOPPE.mapper.ProductMapper;
import com.matrix.SHOPPE.model.dto.ProductDto;
import com.matrix.SHOPPE.model.entity.Product;
import com.matrix.SHOPPE.model.entity.User;
import com.matrix.SHOPPE.service.WishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserRepository userRepository;

    public void addToWishlist(Integer productId, Integer userId) {
        log.info("Adding product to wishlist {}", productId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        user.addToWishlist(product);
        userRepository.save(user);
        log.info("Product added to wishlist {}", productId);
    }

    public void removeFromWishlist(Integer productId, Integer userId) {
        log.info("Removing product from wishlist {}", productId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        user.removeFromWishlist(product);
        userRepository.save(user);
        log.info("Product removed from wishlist {}", productId);
    }

    public List<ProductDto> getWishlistProducts(Integer userId) {
        log.info("Getting wishlist products");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return user.getWishlistProducts().stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

}
