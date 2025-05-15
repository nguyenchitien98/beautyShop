package com.tien.service;

import com.tien.dto.response.RatingResponse;
import com.tien.entity.Rating;

import java.util.List;

public interface RatingService {
    RatingResponse addRating(Long productId, Integer ratingValue, String comment);
    List<RatingResponse> getRatingsForProduct(Long productId);
    Double getAverageRating(Long productId);
}
