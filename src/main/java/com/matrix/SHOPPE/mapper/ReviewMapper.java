package com.matrix.SHOPPE.mapper;

import com.matrix.SHOPPE.model.dto.ReviewAddRequestDto;
import com.matrix.SHOPPE.model.dto.ReviewDto;
import com.matrix.SHOPPE.model.entity.Review;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDto reviewToReviewDto(Review review);

    Review reviewAddRequestToReview(ReviewAddRequestDto reviewAddRequestDto);
}
