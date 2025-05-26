package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.ProductRepository;
import com.matrix.SHOPPE.Repository.ReviewRepository;
import com.matrix.SHOPPE.Repository.UserRepository;
import com.matrix.SHOPPE.exception.ForbiddenException;
import com.matrix.SHOPPE.exception.ProductNotFoundException;
import com.matrix.SHOPPE.exception.ResourceNotFoundException;
import com.matrix.SHOPPE.exception.UserNotFoundException;
import com.matrix.SHOPPE.jwt.JwtService;
import com.matrix.SHOPPE.mapper.ReviewMapper;
import com.matrix.SHOPPE.model.dto.ReviewAddRequestDto;
import com.matrix.SHOPPE.model.dto.ReviewDto;
import com.matrix.SHOPPE.model.entity.Product;
import com.matrix.SHOPPE.model.entity.Review;
import com.matrix.SHOPPE.model.entity.User;
import com.matrix.SHOPPE.service.ReviewService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public List<ReviewDto> getReviews(Integer productId) {
        log.info("Fetching reviews for product ID: {}", productId);
        return reviewRepository.findByProductId(productId).stream().map(reviewMapper::reviewToReviewDto).collect(Collectors.toList());
    }

    @Override
    public ReviewDto addReview(ReviewAddRequestDto reviewAddRequestDto, String token) {
        log.info("Adding new review for product ID: {}", reviewAddRequestDto.getProductId());
        Claims claims = jwtService.validateToken(token.substring("bearer".length()).trim());
        String username = claims.get("sub", String.class);
        Product product = productRepository.findById(reviewAddRequestDto.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product with id " + reviewAddRequestDto.getProductId() + " not found"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
        Review review = new Review();
        review.setProduct(product);
        review.setComment(reviewAddRequestDto.getComment());
        review.setStarRating(reviewAddRequestDto.getStarRating());
        review.setUser(user);

        Review savedReview = reviewRepository.save(review);
        saveRating(reviewAddRequestDto.getProductId());
        return reviewMapper.reviewToReviewDto(savedReview);
    }

    @Override
    public ReviewDto editReview(Integer id, ReviewAddRequestDto review, String token) {
        Review reviewEntity = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review with id " + id + " not found"));
        Claims claims = jwtService.validateToken(token.substring("bearer".length()).trim());
        String username = claims.get("sub", String.class);
        if (!reviewEntity.getUser().getUsername().equals(username)) {
            throw new ForbiddenException("Only the user who created the review can update it");
        }
        reviewEntity.setComment(review.getComment());
        reviewEntity.setStarRating(review.getStarRating());

        Review savedReview = reviewRepository.save(reviewEntity);
        saveRating(reviewEntity.getProduct().getId());
        return reviewMapper.reviewToReviewDto(savedReview);
    }

    @Override
    public void removeReview(Integer id, String token) {
        Review reviewEntity = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review with id " + id + " not found"));
        Claims claims = jwtService.validateToken(token.substring("bearer".length()).trim());
        String username = claims.get("sub", String.class);
        if (!reviewEntity.getUser().getUsername().equals(username)) {
            throw new ForbiddenException("Only the user who created the review can update it");
        }
        reviewRepository.deleteById(id);
        saveRating(reviewEntity.getProduct().getId());
    }

    private void saveRating(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));
        List<Review> reviews = reviewRepository.findByProductId(productId);
        Double averageRating = reviews.stream().mapToDouble(Review::getStarRating).average().orElse(0.0);
        product.setRating(averageRating);
        productRepository.save(product);
    }
}
