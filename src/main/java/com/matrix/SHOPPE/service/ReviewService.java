package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.dto.ReviewAddRequestDto;
import com.matrix.SHOPPE.model.dto.ReviewDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    List<ReviewDto> getReviews(Integer productId);

    ReviewDto addReview(ReviewAddRequestDto review);
}
