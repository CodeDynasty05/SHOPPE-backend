package com.matrix.SHOPPE.controller;

import com.matrix.SHOPPE.model.dto.ReviewAddRequestDto;
import com.matrix.SHOPPE.model.dto.ReviewDto;
import com.matrix.SHOPPE.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{productId}")
    public List<ReviewDto> getReviews(@PathVariable Integer productId) {
        return reviewService.getReviews(productId);
    }

    @PostMapping
    public ReviewDto addReview(@RequestBody ReviewAddRequestDto review) {
        return reviewService.addReview(review);
    }
}
