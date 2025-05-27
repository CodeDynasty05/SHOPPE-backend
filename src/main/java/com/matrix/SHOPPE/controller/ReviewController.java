package com.matrix.SHOPPE.controller;

import com.matrix.SHOPPE.model.dto.ReviewAddRequestDto;
import com.matrix.SHOPPE.model.dto.ReviewDto;
import com.matrix.SHOPPE.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{productId}")
    public List<ReviewDto> getReviews(@PathVariable Integer productId) {
        log.info("Fetching reviews for product ID: {}", productId);
        List<ReviewDto> reviews = reviewService.getReviews(productId);
        log.debug("Found {} reviews for product ID: {}", reviews.size(), productId);
        return reviews;
    }

    @PostMapping
    public ReviewDto addReview(@Valid @RequestBody ReviewAddRequestDto review, @RequestHeader(name = "Authorization") String token) {
        log.info("Adding new review for product ID: {}", review.getProductId());
        ReviewDto savedReview = reviewService.addReview(review, token);
        log.debug("Successfully added review with ID: {}", savedReview.getId());
        return savedReview;
    }

    @PutMapping("/{id}")
    public ReviewDto editReview(@Valid @PathVariable Integer id, @RequestBody ReviewAddRequestDto review, @RequestHeader(name = "Authorization") String token) {
        return reviewService.editReview(id, review, token);
    }

    @DeleteMapping("/{id}")
    public void removeReview(@PathVariable Integer id, @RequestHeader(name = "Authorization") String token) {
        reviewService.removeReview(id, token);
        log.info("Successfully removed review with ID: {}", id);
    }
}