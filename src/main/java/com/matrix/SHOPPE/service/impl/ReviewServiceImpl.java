package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.ProductRepository;
import com.matrix.SHOPPE.Repository.ReviewRepository;
import com.matrix.SHOPPE.Repository.UserRepository;
import com.matrix.SHOPPE.exception.ProductNotFoundException;
import com.matrix.SHOPPE.exception.UserNotFoundException;
import com.matrix.SHOPPE.mapper.ReviewMapper;
import com.matrix.SHOPPE.model.dto.ReviewAddRequestDto;
import com.matrix.SHOPPE.model.dto.ReviewDto;
import com.matrix.SHOPPE.model.entity.Review;
import com.matrix.SHOPPE.model.entity.Product;
import com.matrix.SHOPPE.model.entity.User;
import com.matrix.SHOPPE.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public List<ReviewDto> getReviews(Integer productId) {
        return reviewRepository.findByProductId(productId).stream().map(reviewMapper::reviewToReviewDto).collect(Collectors.toList());
    }

    @Override
    public ReviewDto addReview(ReviewAddRequestDto reviewAddRequestDto) {
        Product product = productRepository.findById(reviewAddRequestDto.getProductId()).orElseThrow(()-> new ProductNotFoundException("Product with id "+reviewAddRequestDto.getProductId()+" not found"));
        User user = userRepository.findById(reviewAddRequestDto.getUserId()).orElseThrow(()-> new UserNotFoundException("User with id "+reviewAddRequestDto.getUserId()+" not found"));
        Review review = new Review();
        review.setProduct(product);
        review.setComment(reviewAddRequestDto.getComment());
        review.setStarRating(reviewAddRequestDto.getStarRating());
        review.setUser(user);

        List<Review> reviews = reviewRepository.findByProductId(reviewAddRequestDto.getProductId());
        Double averageRating = reviews.stream().mapToDouble(Review::getStarRating).average().orElse(0.0);
        product.setRating(averageRating);
        productRepository.save(product);
        return reviewMapper.reviewToReviewDto(reviewRepository.save(review));
    }
}
